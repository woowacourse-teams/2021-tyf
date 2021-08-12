import RefundCertificationForm from '../../../components/Refund/CertificationForm/RefundCertificationForm';
import usePageRefreshGuardEffect from '../../../utils/usePageRefreshGuardEffect';
import { REFUND_PAGE_KEY } from '../Apply/RefundApplyPage';
import { StyledTemplate } from './RefundCertificationPage.styles';

const RefundCertificationPage = () => {
  usePageRefreshGuardEffect(REFUND_PAGE_KEY, false, '/refund');

  return (
    <StyledTemplate>
      <RefundCertificationForm />
    </StyledTemplate>
  );
};

export default RefundCertificationPage;
