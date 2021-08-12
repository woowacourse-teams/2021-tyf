import { SIZE } from '../../../constants/device';
import { useWindowResize } from '../../../utils/useWindowResize';
import DesktopCreatorList from './Desktop/DesktopCreatorList';
import MobileCreatorList from './Mobile/MobileCreatorList';

const CreatorList = () => {
  const { windowWidth } = useWindowResize();

  return windowWidth > SIZE.DESKTOP_LARGE ? <DesktopCreatorList /> : <MobileCreatorList />;
};

export default CreatorList;
