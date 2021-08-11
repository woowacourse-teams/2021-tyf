import { useRecoilValue } from 'recoil';
import { useRefundOrderDetailQuery } from '../@state/refund';

const useRefundOrderDetail = (refundAccessToken: string) => {
  const refundOrderDetail = useRecoilValue(useRefundOrderDetailQuery(refundAccessToken));

  return { refundOrderDetail };
};

export default useRefundOrderDetail;
