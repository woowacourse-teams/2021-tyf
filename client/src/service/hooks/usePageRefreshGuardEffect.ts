import { useEffect } from 'react';
import { useHistory } from 'react-router-dom';
import { atomFamily, useRecoilState } from 'recoil';

const pagePersistenceState = atomFamily({
  key: 'pagePersistenceState',
  default: false,
});

const usePageRefreshGuardEffect = (key: string, isFirstPage: boolean, redirectTo: string) => {
  const history = useHistory();
  const [pagePersistence, setPagepersistence] = useRecoilState(pagePersistenceState(key));

  useEffect(() => {
    if (isFirstPage) {
      setPagepersistence(true);
      return;
    }

    if (pagePersistence) return;

    history.push(redirectTo);
  }, []);
};

export default usePageRefreshGuardEffect;
