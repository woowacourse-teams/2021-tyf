import { useEffect } from 'react';
import { atom, useRecoilState } from 'recoil';

const windowWidthState = atom({
  key: 'windowWidthState',
  default: window.innerWidth,
});

export function useWindowResize() {
  const [windowWidth, setWindowWidth] = useRecoilState(windowWidthState);

  const onResize = () => {
    setWindowWidth(window.innerWidth);
  };

  const useWindowResizeInitEffect = () => {
    useEffect(() => {
      window.addEventListener('resize', onResize);

      return () => {
        window.removeEventListener('resize', onResize);
      };
    }, []);
  };

  return { windowWidth, useWindowResizeInitEffect };
}
