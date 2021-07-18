import { useEffect, useState } from 'react';
import { useRecoilValue, useRecoilValueLoadable } from 'recoil';

import { CreatorId, Donation } from '../../types';
import { DONATION_VIEW_SIZE } from '../../constants/donation';
import { creatorPrivateDonationListQuery, creatorPublicDonationListQuery } from '../state/creator';
import {
  requestCreatorPrivateDonationList,
  requestCreatorPublicDonationList,
} from '../request/creator';
import useAccessToken from './useAccessToken';

const useCreatorDonations = (isAdmin: boolean, creatorId: CreatorId) => {
  const [donationList, setDonationList] = useState<Donation[]>([]);
  const [currentPageIndex, setCurrentPageIndex] = useState(0);
  const { accessToken } = useAccessToken();

  const initDonationList = async () => {
    if (isAdmin) return showMoreDonationList();

    const newDonationList = await requestCreatorPublicDonationList(creatorId);

    setDonationList(newDonationList);
  };

  const showMoreDonationList = async () => {
    const nextDonationList = await requestCreatorPrivateDonationList(
      accessToken,
      currentPageIndex,
      DONATION_VIEW_SIZE
    );
    setDonationList(() => donationList.concat(nextDonationList));
    setCurrentPageIndex(currentPageIndex + 1);
  };

  useEffect(() => {
    initDonationList();
  }, []);

  return { donationList, showMoreDonationList };
};

export default useCreatorDonations;
