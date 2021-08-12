import { atom, atomFamily } from 'recoil';
import { Refund } from '../../types';
import { requestRefundOrderInfo } from '../@request/refund';

export const refundState = atom<Refund>({
  key: 'refundState',
  default: {
    email: '',
    merchantUid: '',
    timeout: 0,
    resendCoolTime: 0,
    refundAccessToken: '',
  },
});

export const useRefundOrderDetailQuery = atomFamily({
  key: 'useRefundOrderDetailQuery',
  default: (refundAccessToken: string) => {
    return requestRefundOrderInfo(refundAccessToken);
  },
});
