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
  const [donationListPageQuery, setDonationListPageQuery] = useState(1);
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
          page: donationListPageQuery,
          size: DONATION_VIEW_SIZE,
        })
      )
    );

    setPrivateDonationList(donationList.concat(newDonationList));
    setDonationListPageQuery(donationListPageQuery + 1);
  };

  useEffect(() => {
    showNextDonationList();
  }, []);

  return { donationList, showNextDonationList };
};

export default useCreatorDonations;
