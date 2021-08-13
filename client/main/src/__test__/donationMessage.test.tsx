import { screen, waitFor } from '@testing-library/dom';
import userEvent from '@testing-library/user-event';

import DonationMessagePage from '../pages/Donation/Message/DonationMessagePage';
import { mockHistoryPush, myRender } from './utils/testUtil';

beforeEach(() => {
  myRender(<DonationMessagePage />);
});

describe('donationMessage', () => {
  test('후원자의 닉네임과 메세지를 입력할 수 있다.', () => {
    // const $nicknameInput = screen.getByRole('nickname-input') as HTMLInputElement;
    // const $messageInput = screen.getByRole('message-input') as HTMLTextAreaElement;
    // const $submitButton = screen.getByRole('button', {
    //   name: '메세지 남기기',
    // }) as HTMLButtonElement;
    // userEvent.type($nicknameInput, '후원자입니다');
    // userEvent.type($messageInput, '화이팅하세요');
    // userEvent.click($submitButton);
    // const historyPushMock = mockHistoryPush();
    // waitFor(() => {
    //   expect(historyPushMock).toHaveBeenCalled();
    // });
  });
});
