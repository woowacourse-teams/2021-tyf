import { useState } from 'react';
import { useRecoilValue } from 'recoil';

import { CreatorId, Donation } from '../../types';
import { DONATION_VIEW_SIZE } from '../../constants/donation';
import { creatorPrivateDonationListQuery, creatorPublicDonationListQuery } from '../state/creator';

const useCreatorDonations = (isAdmin: boolean, creatorId: CreatorId) => {
  const [currentPage, setCurrentPage] = useState(1);
  const [privateDonationList, setPrivateDonationList] = useState<Donation[]>([]);

  const showNextDonationList = () => {
    const newDonationList = JSON.parse(
      JSON.stringify(
        creatorPrivateDonationListQuery({
          page: currentPage,
          size: DONATION_VIEW_SIZE,
        })
      )
    );
    setPrivateDonationList(privateDonationList.concat(newDonationList));
    setCurrentPage(currentPage + 1);
  };

  const donationList = isAdmin
    ? showNextDonationList()
    : useRecoilValue(creatorPublicDonationListQuery(creatorId));
  return { donationList, showNextDonationList };
};

export default useCreatorDonations;
