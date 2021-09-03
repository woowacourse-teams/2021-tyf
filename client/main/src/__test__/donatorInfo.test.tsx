import { screen } from '@testing-library/dom';
import userEvent from '@testing-library/user-event';

import { myRender } from './utils/testUtil';

// beforeEach(() => {
//   myRender(<DonatorInfoPage />);
// });

describe('donatorInfo', () => {
  test('사용자가 이메일과 약관동의를 입력하면 다음버튼이 활성화 된다.', async () => {
    // const $emailInput = screen.getByRole('email-input') as HTMLInputElement;
    // const $termCheckBox = screen.getByRole('checkbox', {
    //   name: /결제 약관 에 동의 \(필수\)/i,
    // });
    // const $submitButton = screen.getByRole('button', { name: '다음' }) as HTMLButtonElement;
    // expect($submitButton).toBeDisabled();
    // userEvent.type($emailInput, 'user123@gmail.com');
    // userEvent.click($termCheckBox);
    // expect($submitButton).not.toBeDisabled();
  });
});
