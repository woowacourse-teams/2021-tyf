import { useRecoilValue } from 'recoil';

import { creatorListQuery } from '../@state/creator';
import { Creator } from '../../types';
import { useEffect, useRef, useState } from 'react';

const useCreatorList = () => {
  const listRef = useRef<HTMLDivElement>(null);
  const creatorList: Creator[] = useRecoilValue(creatorListQuery);
  const [currentPage, setCurrentPage] = useState(0);

  const pageAmount = Math.ceil(creatorList.length / 3) - 1;
  const isLastPage = currentPage >= pageAmount;
  const isFirstPage = currentPage <= 0;

  const showPrevList = () => {
    if (isFirstPage) return;

    setCurrentPage(currentPage - 1);
  };

  const showNextList = () => {
    if (isLastPage) return;

    setCurrentPage(currentPage + 1);
  };

  useEffect(() => {
    if (!listRef.current) return;

    listRef.current.scroll({
      left: currentPage * listRef.current.clientWidth,
      behavior: 'smooth',
    });
  }, [currentPage]);

  return { listRef, creatorList, showPrevList, showNextList, isLastPage, isFirstPage };
};

export default useCreatorList;
