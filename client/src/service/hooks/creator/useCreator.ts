import { useRecoilValue } from 'recoil';
import { CreatorId } from '../../../types';
import { creatorQuery } from '../../state/creator';

const useCreator = (creatorId: CreatorId) => {
  const creator = useRecoilValue(creatorQuery(creatorId));

  return creator;
};

export default useCreator;
