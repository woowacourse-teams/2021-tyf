import { screen, waitFor } from '@testing-library/dom';
import userEvent from '@testing-library/user-event';
import RefundCertificationPage from '../pages/Refund/Certification/RefundCertificationPage';
import { mockAlert, myRender } from './utils/testUtil';

const historyPushMock = jest.fn();
const alertMock = mockAlert();

jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom'),
  useHistory: () => ({
    push: historyPushMock,
  }),
}));

describe('RefundCertificationPage', () => {
  beforeEach(() => {
    myRender(<RefundCertificationPage />);
  });

  test('인증번호를 인증할 수 있다.', async () => {
    const $certificationNumberInput = screen.getByPlaceholderText('인증번호 입력하기');

    const $verifyButton = screen.getByRole('button', { name: '확인' }) as HTMLButtonElement;

    $verifyButton.disabled = false;

    userEvent.type($certificationNumberInput, '123123');
    userEvent.click($verifyButton);

    await waitFor(() => expect(historyPushMock).toBeCalled());
  });

  test('인증 번호를 다시보내기 할 수 있다.', async () => {
    const $resendButton = screen.getByRole('button', {
      name: '인증번호 다시 보내기',
    }) as HTMLButtonElement;

    userEvent.click($resendButton);
    await waitFor(() => expect(alertMock).toHaveBeenCalledWith('이메일이 전송되었습니다.'));
  });
});
