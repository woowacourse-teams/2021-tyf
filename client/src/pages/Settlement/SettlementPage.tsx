import SettlementInfo from '../../components/Settlement/Info/SettlementInfo';
import { StyledTemplate, StyledTitle } from './SettlementPage.styles';

const SettlementPage = () => {
  return (
    <StyledTemplate>
      <StyledTitle>정산 관리</StyledTitle>
      <section>
        <SettlementInfo />
      </section>
    </StyledTemplate>
  );
};

export default SettlementPage;
