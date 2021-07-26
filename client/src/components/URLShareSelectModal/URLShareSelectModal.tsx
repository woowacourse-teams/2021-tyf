import { donationUrlShare } from '../../service/share';
import { UserInfo } from '../../types';
import { Name, StyledContainerButton, StyledModal } from './URLShareSelectModal.styles';

export interface URLShareSelectModalProps {
  userInfo: UserInfo;
  onClose: () => void;
  openBannerShareModal: () => void;
}

const URLShareSelectModal = ({
  userInfo,
  onClose,
  openBannerShareModal,
}: URLShareSelectModalProps) => {
  const shareUrl = () => {
    if (!userInfo) return;

    donationUrlShare(userInfo.nickname, userInfo.pageName);
  };

  return (
    <StyledModal onClose={onClose}>
      <StyledContainerButton onClick={shareUrl}>
        <Name>URL 복사하기</Name>
      </StyledContainerButton>
      <StyledContainerButton onClick={openBannerShareModal}>
        <Name>버튼 공유하기</Name>
      </StyledContainerButton>
    </StyledModal>
  );
};

export default URLShareSelectModal;
