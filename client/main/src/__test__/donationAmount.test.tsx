import { screen } from '@testing-library/dom';
import userEvent from '@testing-library/user-event';

import DonationAmountPage from '../pages/Donation/Amount/DonationAmountPage';
import { accessTokenState } from '../service/@state/login';
import { myRender } from './utils/testUtil';

beforeEach(() => {
  const initializeState = ({ set }: any) => {
    set(accessTokenState, 'mocked');
  };

  myRender(<DonationAmountPage />, initializeState);
});

describe('donationAmount', () => {
  test('사용자는 도네이션 금액을 입력할 수 있다.', async () => {
    const $amountInput = (await screen.findByRole('textbox', {
      name: 'money',
    })) as HTMLInputElement;
    const $donateButton = await screen.findByRole('button', {
      name: '도네이션 하기',
    });
    expect($donateButton).toBeDisabled();
    expect($amountInput.value).toBe('');
    userEvent.type($amountInput, '30000');
    expect($donateButton).not.toBeDisabled();
    expect($amountInput.value).toBe('30000');
  });

  test('사용자는 도네이션 금액을 금액추가 버튼을 눌러 증가시킬 수 있다.', async () => {
    const $moneyInput = (await screen.findByRole('textbox', { name: 'money' })) as HTMLInputElement;
    const $add1000Button = await screen.findByRole('button', {
      name: /\+1,000tp/i,
    });
    userEvent.click($add1000Button);
    expect($moneyInput.value).toBe('1000');
  });
});
