import { useEffect, useState } from 'react';
import { useRecoilValue } from 'recoil';

import { CreatorId, Donation } from '../../types';
import { DONATION_VIEW_SIZE } from '../../constants/donation';
import { creatorPrivateDonationListQuery, creatorPublicDonationListQuery } from '../state/creator';

const useCreatorDonations = (isAdmin: boolean, creatorId: CreatorId) => {
  const [currentPage, setCurrentPage] = useState(0);
  const [privateDonationList, setPrivateDonationList] = useState<Donation[]>([]);
  const newDonationList = useRecoilValue(
    creatorPrivateDonationListQuery({
      page: currentPage,
      size: DONATION_VIEW_SIZE,
    })
  );
  console.log(newDonationList);

  const showNextDonationList = () => {
    setPrivateDonationList(privateDonationList.concat(newDonationList));

    setCurrentPage(currentPage + 1);
  };

  const donationList = isAdmin
    ? privateDonationList
    : useRecoilValue(creatorPublicDonationListQuery(creatorId));

  useEffect(() => {
    showNextDonationList();
  }, []);
  return { donationList, showNextDonationList };
};

export default useCreatorDonations;
