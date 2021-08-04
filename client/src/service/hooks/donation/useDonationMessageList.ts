import { useEffect, useState } from 'react';

import { CreatorId, Donation } from '../../../types';
import { DONATION_MESSAGE_AMOUNT_PER_PAGE } from '../../../constants/donation';
import {
  requestCreatorPrivateDonationList,
  requestCreatorPublicDonationList,
} from '../../request/creator';
import useAccessToken from '../auth/useAccessToken';
import { AUTH_ERROR } from '../../../constants/error';

const useDonationMessageList = (isAdmin: boolean, creatorId: CreatorId) => {
  const [donationList, setDonationList] = useState<Donation[]>([]);
  const [currentPageIndex, setCurrentPageIndex] = useState(0);
  const [hasMoreList, setHasMoreList] = useState(true);
  const { accessToken } = useAccessToken();

  const initDonationList = async () => {
    if (isAdmin) {
      try {
        return showMoreDonationList();
      } catch (error) {
        const { errorCode } = error.response.data;
        if (errorCode === AUTH_ERROR.INVALID_TOKEN) {
          alert('로그인이 만료되었습니다.');
          window.location.reload();
        }
      }
    }

    const newDonationList = await requestCreatorPublicDonationList(creatorId);

    setDonationList(newDonationList);
    setHasMoreList(false);
  };

  const showMoreDonationList = async () => {
    const nextDonationList = await requestCreatorPrivateDonationList(
      accessToken,
      currentPageIndex,
      DONATION_MESSAGE_AMOUNT_PER_PAGE
    );

    if (nextDonationList.length < DONATION_MESSAGE_AMOUNT_PER_PAGE) {
      setHasMoreList(false);
    }

    setDonationList(() => donationList.concat(nextDonationList));
    setCurrentPageIndex(currentPageIndex + 1);
  };

  useEffect(() => {
    initDonationList();
  }, []);

  return { donationList, showMoreDonationList, hasMoreList };
};

export default useDonationMessageList;
