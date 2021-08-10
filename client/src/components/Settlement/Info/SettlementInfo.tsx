import { SIZE } from '../../../constants/device';
import useModal from '../../../utils/useModal';
import { useWindowResize } from '../../../utils/useWindowResize';
import SettlementAccount from '../Account/SettlementAccount';
import DesktopSettlementInfo from './Desktop/DesktopSettlementInfo';
import MobileSettlementInfo from './Mobile/MobileSettlementInfo';

const SettlementInfo = () => {
  const { isOpen, closeModal } = useModal();
  const { windowWidth } = useWindowResize();
  // const {} = useSettlement();

  return (
    <>
      {windowWidth > SIZE.DESKTOP_LARGE ? <DesktopSettlementInfo /> : <MobileSettlementInfo />}
      {!isOpen && <SettlementAccount onClose={closeModal} />}
    </>
  );
};

export default SettlementInfo;
