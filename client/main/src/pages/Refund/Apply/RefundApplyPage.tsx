import RefundApplyForm from '../../../components/Refund/ApplyForm/RefundApplyForm';
import usePageRefreshGuardEffect from '../../../utils/usePageRefreshGuardEffect';
import { RefundApplyPageTemplate } from './RefundApplyPage.styles';

export const REFUND_PAGE_KEY = 'refund';

const RefundApplyPage = () => {
  usePageRefreshGuardEffect(REFUND_PAGE_KEY, true, '/refund');

  return (
    <RefundApplyPageTemplate>
      <RefundApplyForm />
    </RefundApplyPageTemplate>
  );
};

export default RefundApplyPage;
