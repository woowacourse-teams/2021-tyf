import useStatistics from '../../service/hooks/user/useStatistics';
import useUserInfo from '../../service/hooks/user/useUserInfo';
import { toCommaSeparatedString } from '../../utils/format';
import { StyledTemplate, InfoTitle, MoneyInfo } from './StatisticsPage.styles';

const StatisticsPage = () => {
  const { userInfo } = useUserInfo();
  const { totalAmount } = useStatistics();

  return (
    <StyledTemplate>
      <InfoTitle>
        {userInfo && userInfo.nickname}님이
        <br />총 후원 받은 금액은
      </InfoTitle>
      <MoneyInfo>
        <span role="total-amount">{toCommaSeparatedString(totalAmount ?? 0)}</span>
        <span>원</span>
      </MoneyInfo>
    </StyledTemplate>
  );
};

export default StatisticsPage;
