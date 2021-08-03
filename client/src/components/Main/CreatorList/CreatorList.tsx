import { SIZE } from '../../../constants/device';
import useCreatorList from '../../../service/hooks/useCreatorList';
import { useWindowResize } from '../../../utils/useWindowResize';
import Anchor from '../../@atom/Anchor/Anchor';
import CreatorCard from '../CreatorCard/CreatorCard';
import DesktopCreatorList from './Desktop/DesktopCreatorList';
import MobileCreatorList from './Mobile/MobileCreatorList';

const CreatorList = () => {
  const { windowWidth } = useWindowResize();

  return windowWidth > SIZE.DESKTOP_LARGE ? <DesktopCreatorList /> : <MobileCreatorList />;
};

export default CreatorList;
