import { useRecoilValue } from 'recoil';

import { creatorListQuery } from './../state/creator';
import { Creator } from './../../types';

const useCreatorList = () => {
  const creatorList: Creator[] = useRecoilValue(creatorListQuery);

  return { creatorList };
};

export default useCreatorList;
