import useLoginUserInfo from '../../service/hooks/useLoginUserInfo';
import useStatistics from '../../service/hooks/useStatistics';
import { StyledTemplate, InfoTitle, MoneyInfo } from './StatisticsPage.styles';

const StatisticsPage = () => {
  const { userInfo } = useLoginUserInfo();
  const { totalAmount } = useStatistics();

  return (
    <StyledTemplate>
      <InfoTitle>
        {userInfo && userInfo.nickname}님이
        <br />총 후원 받은 금액은
      </InfoTitle>
      <MoneyInfo>
        <span role="total-amount">{(totalAmount ?? 0).toLocaleString('en-us')}</span>
        <span>원</span>
      </MoneyInfo>
    </StyledTemplate>
  );
};

export default StatisticsPage;
