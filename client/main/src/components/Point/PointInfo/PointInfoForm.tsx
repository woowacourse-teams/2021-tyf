import useUserInfo from '../../../service/user/useUserInfo';
import { toCommaSeparatedString } from '../../../utils/format';
import Transition from '../../@atom/Transition/Transition.styles';
import { InfoTitle, MoneyInfo, StyleButton, StyledPointForm } from './PointInfoForm.styles';

const PointInfoForm = () => {
  const { userInfo } = useUserInfo();

  return (
    <StyledPointForm>
      <Transition>
        <InfoTitle>
          {userInfo && userInfo.nickname}님이
          <br />
          도네이션 할 수 있는
          <br />
          포인트는
        </InfoTitle>
      </Transition>
      <Transition delay={0.2}>
        <MoneyInfo>
          <span>{(userInfo && userInfo.point && toCommaSeparatedString(userInfo.point)) ?? 0}</span>
          <span>tp</span>
        </MoneyInfo>
      </Transition>
      <Transition delay={0.4}>
        <StyleButton>충전하기</StyleButton>
      </Transition>
    </StyledPointForm>
  );
};

export default PointInfoForm;
