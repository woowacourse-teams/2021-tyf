import { useEffect, useState } from 'react';
import { useHistory } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

import {
  requestAgreeExchange,
  requestDeclineExchange,
  requestExchangeList,
} from '../request/settlement';
import { Exchange } from '../type';
import { accessTokenState } from './@state/login';

const useSettlement = () => {
  const history = useHistory();
  const [exchangeList, setExchangeList] = useState<Exchange[]>([]);
  const accessToken = useRecoilValue(accessTokenState);

  const getExchangeList = async () => {
    try {
      const data: Exchange[] = await requestExchangeList(accessToken);

      setExchangeList(data);
    } catch (error) {
      alert(error.message);

      if (error.errorCode === 'auth-002') {
        history.push('/');
      }
    }
  };

  const agreeExchange = async (pageName: string) => {
    try {
      await requestAgreeExchange(pageName, accessToken);

      alert('정산을 수락했습니다.');
      // TODO : 요소 제거후 새로고침해서 요소 갱신 개선하기
      // setExchangeList(exchangeList.filter(item => item.pageName !== pageName)
      location.reload();
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
      location.reload();
    } catch (error) {
      alert(error.message);
    }
  };

  useEffect(() => {
    getExchangeList();
  }, []);

  return { exchangeList, agreeExchange, declineExchange };
};

export default useSettlement;
