import { useEffect } from 'react';

const useInitScrollTopEffect = (...dependencies: unknown[]) => {
  useEffect(() => {
    window.scroll({ top: 0 });
  }, dependencies);
};

export default useInitScrollTopEffect;
