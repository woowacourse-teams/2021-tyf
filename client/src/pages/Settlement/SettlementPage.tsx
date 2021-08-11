import { Suspense, useEffect } from 'react';
import { ErrorBoundary } from 'react-error-boundary';
import { useHistory } from 'react-router-dom';

import SettlementInfo from '../../components/Settlement/Info/SettlementInfo';
import SettlementRegisterForm from '../../components/Settlement/Register/SettlementRegisterForm';
import SettlementRegisterCancelledInfo from '../../components/Settlement/RegisterCancelled/SettlementRegisterCancelledInfo';
import SettlementRegisterPendingInfo from '../../components/Settlement/RegisterPending/SettlementRegisterPendingInfo';
import Spinner from '../../components/Spinner/Spinner';
import useSettlement from '../../service/settlement/useSettlement';
import { StyledTemplate, StyledTitle } from './SettlementPage.styles';

const SettlementPage = () => {
  const { account } = useSettlement();
  const history = useHistory();

  const component: Record<string, () => JSX.Element> = {
    UNREGISTERED: SettlementRegisterForm,
    REGISTERED: SettlementInfo,
    REQUESTING: SettlementRegisterPendingInfo,
    CANCELLED: SettlementRegisterCancelledInfo,
  };

  const Settlement = component[account.status] ?? SettlementRegisterForm;

  useEffect(() => {
    if (!Settlement) {
      history.push('/settlement/register');
    }
  }, [Settlement]);

  return (
    <StyledTemplate>
      <StyledTitle>정산 관리</StyledTitle>
      <section>
        <Suspense fallback={Spinner}>
          <Settlement />
        </Suspense>
      </section>
    </StyledTemplate>
  );
};

export default SettlementPage;
