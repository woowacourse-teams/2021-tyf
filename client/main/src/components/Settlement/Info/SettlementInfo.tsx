import { SIZE } from '../../../constants/device';
import { useWindowResize } from '../../../utils/useWindowResize';
import DesktopSettlementInfo from './Desktop/DesktopSettlementInfo';
import MobileSettlementInfo from './Mobile/MobileSettlementInfo';

const SettlementInfo = () => {
  const { windowWidth } = useWindowResize();

  return windowWidth > SIZE.DESKTOP_EXTRA ? <DesktopSettlementInfo /> : <MobileSettlementInfo />;
};

export default SettlementInfo;
