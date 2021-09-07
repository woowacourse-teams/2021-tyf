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
    // 창작자정보, 후원정보 잘 보이는지 테스트
    const { creator, donation } = refundInfoMock;
    //창작자명
    await screen.findByText(creator.nickname);
    //후원금액
    await screen.findByText(donation.donatedPoint);
    //후원일자
    await screen.findByText(donation.createdAt);
    //후원자명
    await screen.findByText(donation.name);
    //후원자 메세지
    await screen.findByText(donation.message);
  });

  test('환불 신청을 할 수 있다.', async () => {
    const $refundButton = screen.getByRole('button', {
      name: '환불 신청하기',
    });

    userEvent.click($refundButton);

    await waitFor(() => expect(alertMock).toHaveBeenCalledWith('환불 신청이 완료 되었습니다.'));
  });
});
