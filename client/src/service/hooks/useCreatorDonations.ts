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
  const [hasMoreList, setHasMoreList] = useState(true);
  const { accessToken } = useAccessToken();

  const initDonationList = async () => {
    if (isAdmin) return showMoreDonationList();

    const newDonationList = await requestCreatorPublicDonationList(creatorId);

    setDonationList(newDonationList);
    setHasMoreList(false);
  };

  const showMoreDonationList = async () => {
    const nextDonationList = await requestCreatorPrivateDonationList(
      accessToken,
      currentPageIndex,
      DONATION_VIEW_SIZE
    );

    if (nextDonationList.length < DONATION_VIEW_SIZE) {
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

export default useCreatorDonations;
