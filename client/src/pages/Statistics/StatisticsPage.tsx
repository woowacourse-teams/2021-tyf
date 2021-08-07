import Transition from '../../components/@atom/Transition/Transition.styles';
import useStatistics from '../../service/hooks/user/useStatistics';
import useUserInfo from '../../service/hooks/user/useUserInfo';
import { toCommaSeparatedString } from '../../utils/format';
import { StyledTemplate, InfoTitle, MoneyInfo } from './StatisticsPage.styles';

const StatisticsPage = () => {
  const { userInfo } = useUserInfo();
  const { totalAmount } = useStatistics();

  return (
    <StyledTemplate>
      <Transition>
        <InfoTitle>
          {userInfo && userInfo.nickname}님이
          <br />총 후원 받은 금액은
        </InfoTitle>
      </Transition>
      <Transition delay={0.2}>
        <MoneyInfo>
          <span role="total-amount">{toCommaSeparatedString(totalAmount ?? 0)}</span>
          <span>원</span>
        </MoneyInfo>
      </Transition>
    </StyledTemplate>
  );
};

export default StatisticsPage;
