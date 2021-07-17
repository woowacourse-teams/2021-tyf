import { useEffect, useState } from 'react';
import { useRecoilState, useRecoilValue } from 'recoil';

import { CreatorId } from '../../types';
import { DONATION_VIEW_SIZE } from '../../constants/donation';
import {
  creatorPrivateDonationListQuery,
  creatorPublicDonationListQuery,
  donationListState,
} from '../state/creator';

interface Props {
  isAdmin: boolean;
  creatorId: CreatorId;
}

const useCreatorDonations = ({ isAdmin, creatorId }: Props) => {
  const [currentPage, setCurrentPage] = useState(1);
  const [privateDonationList, setPrivateDonationList] = useRecoilState(
    donationListState(creatorId)
  );
  const donationList = isAdmin
    ? privateDonationList
    : useRecoilValue(creatorPublicDonationListQuery(creatorId));

  const showNextDonationList = () => {
    const newDonationList = JSON.parse(
      JSON.stringify(
        creatorPrivateDonationListQuery({
          page: currentPage,
          size: DONATION_VIEW_SIZE,
        })
      )
    );

    setPrivateDonationList(donationList.concat(newDonationList));
    setCurrentPage(currentPage + 1);
  };

  useEffect(() => {
    showNextDonationList();
  }, []);

  return { donationList, showNextDonationList };
};

export default useCreatorDonations;
