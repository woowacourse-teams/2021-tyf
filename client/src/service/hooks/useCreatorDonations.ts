import { useRecoilValue } from 'recoil';

import { CreatorId } from '../../types';
import { DONATION_VIEW_SIZE } from '../../constants/donation';
import { creatorPrivateDonationListQuery, creatorPublicDonationListQuery } from '../state/creator';

interface Props {
  isAdmin: boolean;
  creatorId: CreatorId;
  page: number;
}
const useCreatorDonations = ({ isAdmin, creatorId, page }: Props) => {
  const donationList = isAdmin
    ? useRecoilValue(creatorPrivateDonationListQuery({ page, size: DONATION_VIEW_SIZE }))
    : useRecoilValue(creatorPublicDonationListQuery(creatorId));

  return { donationList };
};

export default useCreatorDonations;
