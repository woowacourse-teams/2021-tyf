import { donationUrlShare } from '../../../service/share';
import { UserInfo } from '../../../types';
import LinkIcon from '../../../assets/icons/link.svg';
import {
  Icon,
  ImgIcon,
  Name,
  StyledContainerButton,
  StyledModal,
} from './URLShareSelectModal.styles';
import useURLBanner from '../../../service/hooks/user/useURLBanner';

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
  const { bannerURL } = useURLBanner(userInfo);

  const shareUrl = () => {
    if (!userInfo) return;

    donationUrlShare(userInfo.nickname, userInfo.pageName);
  };

  return (
    <StyledModal onClose={onClose}>
      <StyledContainerButton onClick={shareUrl}>
        <Icon src={LinkIcon} alt="URL 복사" />
        <Name>URL 복사하기</Name>
      </StyledContainerButton>
      <StyledContainerButton onClick={openBannerShareModal}>
        <ImgIcon src={bannerURL} alt="버튼 공유하기" />
        <Name>버튼 공유하기</Name>
      </StyledContainerButton>
    </StyledModal>
  );
};

export default URLShareSelectModal;
