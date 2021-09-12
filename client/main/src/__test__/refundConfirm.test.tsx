import { screen, waitFor } from '@testing-library/dom';
import userEvent from '@testing-library/user-event';
import { refundInfoMock } from '../mock/mockData';
import RefundConfirmPage from '../pages/Refund/Confirm/RefundConfirmPage';
import { toCommaSeparatedString } from '../utils/format';
import { myRender, mockAlert } from './utils/testUtil';

const alertMock = mockAlert();

describe('RefundConfirmPage', () => {
  beforeEach(() => {
    myRender(<RefundConfirmPage />);
  });

  test('환불 대상 결제의 정보가 보인다.', async () => {
    // 충전포인트
    await screen.findByText(new RegExp(toCommaSeparatedString(refundInfoMock.point)));
    // 결제금액
    await screen.findByText(new RegExp(toCommaSeparatedString(refundInfoMock.price)));
    // 결제일자
    await screen.findByText(new RegExp(refundInfoMock.createdAt));
  });

  test('환불 신청을 할 수 있다.', async () => {
    const $refundButton = screen.getByRole('button', {
      name: '환불 신청하기',
    });

    userEvent.click($refundButton);

    await waitFor(() => expect(alertMock).toHaveBeenCalledWith('환불 신청이 완료 되었습니다.'));
  });
});
