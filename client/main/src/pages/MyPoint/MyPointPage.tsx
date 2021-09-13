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
import Transition from '../../components/@atom/Transition/Transition.styles';

const MyPointPage = () => {
  const { userInfo } = useUserInfo();
  const { isOpen, openModal, closeModal } = useModal();

  return (
    <StyledTemplate>
      <StyledContainer>
        <section>
          <Transition>
            <StyledTitle>
              {userInfo?.nickname}님이
              <br />
              도네이션 할 수 있는
              <br />
              포인트는
            </StyledTitle>
          </Transition>
          <Transition delay={0.2}>
            <div>
              <Point>{userInfo?.point && toCommaSeparatedString(userInfo.point)}</Point>
              <span>tp</span>
            </div>
          </Transition>
        </section>
        <section>
          <Transition delay={0.4}>
            <ChargeButton onClick={openModal}>충전하기</ChargeButton>
          </Transition>
        </section>
      </StyledContainer>
      {isOpen && <PointChargeModal closeModal={closeModal} />}
    </StyledTemplate>
  );
};

export default MyPointPage;
