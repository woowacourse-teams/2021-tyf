import { useEffect, useState } from 'react';

let onScrollThrottle: null | NodeJS.Timeout;

const useScroll = () => {
  const [isScrollEnd, setIsScrollEnd] = useState(false);

  const onScroll = () => {
    if (!onScrollThrottle) {
      onScrollThrottle = setTimeout(() => {
        onScrollThrottle = null;
        console.log('run');
        setIsScrollEnd(window.scrollY >= window.innerHeight);
      }, 1000);
    }
  };

  useEffect(() => {
    window.addEventListener('scroll', onScroll);

    return () => {
      window.removeEventListener('scroll', onScroll);
    };
  }, []);

  return { isScrollEnd };
};

export default useScroll;
