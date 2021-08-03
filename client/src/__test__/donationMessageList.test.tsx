import { fireEvent, screen, waitFor } from '@testing-library/dom';
import userEvent from '@testing-library/user-event';
import { act } from 'react-dom/test-utils';

import DonationMessageList from '../components/Donation/MessageList/DonationMessageList';
import { myRender } from './utils/testUtil';

describe('DonationMessageList', () => {
  test('창작자 본인이 아닐 때, 도네이션 메세지 목록이 보인다', async () => {
    myRender(<DonationMessageList isAdmin={false} />);

    await screen.findAllByRole('donation-message');
  });

  test('창작자 본인일 때, 도네이션 메세지 목록이 보인다', async () => {
    myRender(<DonationMessageList isAdmin={true} />);

    await screen.findAllByRole('donation-message');
  });

  test('창작자 본인이 아니면 더보기 버튼이 보이지않는다', async () => {
    myRender(<DonationMessageList isAdmin={false} />);

    await screen.findAllByRole('donation-message');

    const moreButton = screen.queryByRole('button', { name: /더보기/i });

    expect(moreButton).not.toBeInTheDocument();
  });

  test('창작자 본인이면 더보기 버튼이 보인다', async () => {
    myRender(<DonationMessageList isAdmin={true} />);

    await screen.findAllByRole('donation-message');

    await screen.findAllByRole('button', { name: /더보기/i });
  });

  test('더보기 버튼을 누르면 도네이션 메세지 목록이 더 추가된다', async () => {
    //   myRender(<DonationMessageList isAdmin={true} />);
    //   // const prevDonationMessages = await screen.findAllByRole('donation-message');
    //   jest.useFakeTimers();
    //   const moreButton = await screen.findByRole('button', { name: /더보기/i });
    //   // const promise = Promise.resolve();
    //   await userEvent.click(moreButton);
    //   await waitFor(() => {
    //     const donationMessages = screen.getAllByRole('donation-message');
    //     console.log(donationMessages.length);
    //     // expect(prevDonationMessages.length).toBe(donationMessages.length);
    //   });
  });

  test('더 불러올 메세지가 없다면 더보기 버튼은 보이지 않는다.', async () => {
    // myRender(<DonationMessageList isAdmin={true} />);
    // const moreButton = screen.getByRole('button', { name: /더보기/i });
    // fireEvent.click(moreButton);
  });
});

// profile 불러오기 구현하기
