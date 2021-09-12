import { IamportResponse, RequestPayParams } from '../../iamport';
import { requestPayment, requestPaymentComplete } from '../@request/payments';

export const pay = async (
  pg: string,
  itemId: string,
  accessToken: string,
  onFinish?: () => void
) => {
  const { merchantUid, amount, email, itemName } = await requestPayment(itemId, accessToken);
  const { IMP } = window;

  const prodAccountId = 'imp52497817';
  const devAccountId = 'imp61348931';

  const accountId = process.env.NODE_ENV === 'development' ? devAccountId : prodAccountId;

  IMP.init(accountId);

  const IMPRequestPayOption: RequestPayParams = {
    pg,
    pay_method: 'card',
    merchant_uid: merchantUid,
    name: itemName,
    amount,
    buyer_email: email,
  };

  const IMPResponseHandler = async (response: IamportResponse) => {
    if (!response.success) {
      alert(`결제에 실패했습니다. 다시 시도해주세요.\n 에러 내역: ${response.error_msg}`);
      return;
    }

    try {
      await requestPaymentComplete(response, accessToken);
      alert('결제에 성공했습니다.');
    } catch (error) {
      alert(`결제에 실패했습니다. 다시 시도해주세요. ${error.message}`);
    }

    if (onFinish) onFinish();
  };

  IMP.request_pay(IMPRequestPayOption, IMPResponseHandler);

  return { pay };
};
