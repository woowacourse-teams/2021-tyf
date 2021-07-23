import { useState, useEffect } from 'react';

export function useWindowResize() {
  const [windowWidth, setWindowWidth] = useState(window.innerWidth);

  const onResize = () => {
    setWindowWidth(window.innerWidth);
  };

  useEffect(() => {
    window.addEventListener('resize', onResize);

    return () => {
      window.removeEventListener('resize', onResize);
    };
  }, []);

  return { windowWidth };
}
