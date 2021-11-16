import { useEffect, useState } from 'react';
import { throttleGenerator } from './throttle';

const throttle = throttleGenerator(1000);

const useScroll = () => {
  const [isScrollEnd, setIsScrollEnd] = useState(false);

  const onScroll = () => {
    throttle(() => setIsScrollEnd(window.scrollY >= window.innerHeight));
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
