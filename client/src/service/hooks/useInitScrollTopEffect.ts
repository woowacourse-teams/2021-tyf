import { useEffect } from 'react';

const useInitScrollTopEffect = (...dependancies: unknown[]) => {
  useEffect(() => {
    window.scroll({ top: 0 });
  }, dependancies);
};

export default useInitScrollTopEffect;
