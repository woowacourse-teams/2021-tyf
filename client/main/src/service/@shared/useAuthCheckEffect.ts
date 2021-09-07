import { useEffect } from 'react';
import useAccessToken from './useAccessToken';

const useAuthCheckEffect = (callback: () => void) => {
  const { accessToken } = useAccessToken();
  const isAuthed = !!accessToken;

  useEffect(() => {
    if (!isAuthed) callback();
  }, []);
};

export default useAuthCheckEffect;
