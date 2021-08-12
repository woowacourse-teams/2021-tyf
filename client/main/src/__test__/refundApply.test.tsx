import { screen, waitFor } from '@testing-library/dom';
import userEvent from '@testing-library/user-event';
import RefundApplyPage from '../pages/Refund/Apply/RefundApplyPage';
import { mockAlert, mockHistoryPush, myRender } from './utils/testUtil';

describe('RefundApplyPage', () => {
  beforeEach(() => {
    myRender(<RefundApplyPage />);
  });

  test('주문번호를 인증할 수 있다.', async () => {
    const $merchantUidInput = screen.getByRole('textbox');
    const $sendButton = screen.getByRole('button', {
      name: /인증 메일 보내기/i,
    });
    const historyPushMock = mockHistoryPush();
    const alertMock = mockAlert();

    userEvent.type($merchantUidInput, '123123123');
    userEvent.click($sendButton);

    await waitFor(() => expect(alertMock).toHaveBeenCalledWith('이메일이 전송되었습니다.'));
  });
});
