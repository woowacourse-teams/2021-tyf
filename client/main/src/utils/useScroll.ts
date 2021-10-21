import { useEffect, useState } from 'react';

const useScroll = () => {
  const [isScrollEnd, setIsScrollEnd] = useState(false);

  const onScroll = () => {
    setIsScrollEnd(window.scrollY >= window.innerHeight);
  };

  const useScrollEffect = () => {
    useEffect(() => {
      window.requestAnimationFrame(() => {
        window.addEventListener('scroll', onScroll);
      });

      return () => {
        window.removeEventListener('resize', onScroll);
      };
    }, []);
  };

  return { isScrollEnd, useScrollEffect };
};

export default useScroll;
