import { useEffect, useState } from 'react';
import { useHistory } from 'react-router-dom';

import {
  requestAgreeExchange,
  requestDeclineExchange,
  requestExchangeList,
} from '../components/request/request';
import { Exchange, ExchangeListResponse } from '../type';

const useSettlement = () => {
  const history = useHistory();
  const [exchangeList, setExchangeList] = useState<Exchange[]>([]);
  const [accessToken, setAccessToken] = useState('');

  const getAccessToken = () => {
    const token = sessionStorage.getItem('adminToken');
    if (!token) {
      alert('로그인 후 접근가능합니다.');
      history.push('/');
      return;
    }

    setAccessToken(token);
  };

  const getExchangeList = async () => {
    try {
      const { data }: ExchangeListResponse = await requestExchangeList(accessToken);

      setExchangeList(data!);
    } catch (error) {
      alert(error.message);
    }
  };

  const agreeExchange = async (pageName: string) => {
    try {
      await requestAgreeExchange(pageName, accessToken);

      alert('정산을 수락했습니다.');
    } catch (error) {
      alert(error.message);
    }
  };

  const declineExchange = async (pageName: string) => {
    try {
      const reason = window.prompt('거절 사유를 적어주세요');
      if (!reason) {
        throw Error('거절 사유가 작성되지 않았습니다.');
      }

      await requestDeclineExchange(pageName, reason, accessToken);

      alert('정산을 거절했습니다.');
    } catch (error) {
      alert(error.message);
    }
  };

  useEffect(() => {
    getAccessToken();
    getExchangeList();
  }, []);

  return { exchangeList, agreeExchange, declineExchange };
};

export default useSettlement;
