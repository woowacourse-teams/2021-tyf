import { useWindowResize } from '../../utils/useWindowResize';
import { SIZE } from '../../constants/device';
import MobileMenu from './Mobile/MobileMenu';
import DesktopMenu from './Desktop/DesktopMenu';

export interface MenuProps {
  onClose: () => void;
}

const Menu = ({ onClose }: MenuProps) => {
  const { windowWidth } = useWindowResize();

  return windowWidth > SIZE.DESKTOP_LARGE ? (
    <DesktopMenu onClose={onClose} />
  ) : (
    <MobileMenu onClose={onClose} />
  );
};

export default Menu;
