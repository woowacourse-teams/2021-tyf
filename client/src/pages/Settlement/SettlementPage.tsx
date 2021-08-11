import { Suspense, useEffect } from 'react';
import { useHistory } from 'react-router-dom';
import Transition from '../../components/@atom/Transition/Transition.styles';

import SettlementInfo from '../../components/Settlement/Info/SettlementInfo';
import SettlementRegisterForm from '../../components/Settlement/Register/SettlementRegisterForm';
import SettlementRegisterCancelledInfo from '../../components/Settlement/RegisterCancelled/SettlementRegisterCancelledInfo';
import SettlementRegisterPendingInfo from '../../components/Settlement/RegisterPending/SettlementRegisterPendingInfo';
import Spinner from '../../components/Spinner/Spinner';
import useSettlement from '../../service/settlement/useSettlement';
import { SettlementAccountStatus } from '../../types';
import { StyledTemplate, StyledTitle } from './SettlementPage.styles';

const SettlementPage = () => {
  const { account } = useSettlement();
  const history = useHistory();

  const component: Record<SettlementAccountStatus, () => JSX.Element> = {
    UNREGISTERED: SettlementRegisterForm,
    REGISTERED: SettlementInfo,
    REQUESTING: SettlementRegisterPendingInfo,
    REJECTED: SettlementRegisterCancelledInfo,
  };

  const Settlement = component[account.status] ?? SettlementRegisterForm;

  useEffect(() => {
    if (!Settlement) {
      history.push('/');
    }
  }, [Settlement]);

  return (
    <StyledTemplate>
      <StyledTitle>정산 관리</StyledTitle>
      <section>
        <Suspense fallback={Spinner}>
          <Transition>
            <Settlement />
          </Transition>
        </Suspense>
      </section>
    </StyledTemplate>
  );
};

export default SettlementPage;
