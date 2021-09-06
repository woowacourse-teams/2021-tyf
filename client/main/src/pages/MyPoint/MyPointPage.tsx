import {
  StyledTemplate,
  StyledTitle,
  ChargeButton,
  Point,
  StyledContainer,
} from './MyPointPage.styles';
import { toCommaSeparatedString } from '../../utils/format';
import useUserInfo from '../../service/user/useUserInfo';
import PointChargeModal from '../../components/Point/ChargeModal/PointChargeModal';
import useModal from '../../utils/useModal';

const MyPointPage = () => {
  const { userInfo } = useUserInfo();
  const { isOpen, openModal, closeModal } = useModal();

  return (
    <StyledTemplate>
      <StyledContainer>
        <StyledTitle>
          {userInfo?.nickname}님이
          <br />
          도네이션 할 수 있는
          <br />
          포인트는
        </StyledTitle>
        <div>
          <Point>{userInfo && toCommaSeparatedString(userInfo?.point)}</Point>
          <span>tp</span>
        </div>
        <ChargeButton onClick={openModal}>충전하기</ChargeButton>
      </StyledContainer>
      {isOpen && <PointChargeModal closeModal={closeModal} />}
    </StyledTemplate>
  );
};

export default MyPointPage;
