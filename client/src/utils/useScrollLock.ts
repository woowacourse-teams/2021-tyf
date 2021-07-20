import { useEffect } from 'react';

const useScrollLock = () => {
  useEffect(() => {
    document.body.style.overflow = 'hidden';
    return () => {
      document.body.style.overflow = 'unset';
    };
  }, []);
};

export default useScrollLock;
