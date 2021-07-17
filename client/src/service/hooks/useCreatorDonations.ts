import { useRecoilValue } from 'recoil';

import { creatorPublicDonationListQuery } from '../state/creator';

const useCreatorDonations = (creatorId: string) => {
  // TODO: 사용자일때 후원목록 불러오기 구현
  // isAdmin 받아서 조건부 렌더링
  const creatorPublicDonationList = useRecoilValue(creatorPublicDonationListQuery(creatorId));

  return { donationList: creatorPublicDonationList };
};

export default useCreatorDonations;
