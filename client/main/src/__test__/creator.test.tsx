import { screen, waitFor } from '@testing-library/dom';
import { act } from '@testing-library/react';
import userEvent from '@testing-library/user-event';

import DonationMessageList from '../components/Creator/MessageList/DonationMessageList';
import { donationMessageListMock, userInfoMock } from '../mock/mockData';
import CreatorPage from '../pages/Creator/CreatorPage';
import { myRender } from './utils/testUtil';

describe('DonationMessageList', () => {
  test('창작자 본인이 아닐 때, 도네이션 메세지 목록이 보인다', async () => {
    myRender(<DonationMessageList isAdmin={false} />);

    await screen.findAllByRole('listitem', { name: 'donation-message' });
  });

  test('창작자 본인일 때, 도네이션 메세지 목록이 보인다', async () => {
    myRender(<DonationMessageList isAdmin={true} />);

    await screen.findAllByRole('listitem', { name: 'donation-message' });
  });

  test('창작자 본인이 아니면 더보기 버튼이 보이지않는다', async () => {
    myRender(<DonationMessageList isAdmin={false} />);

    await screen.findAllByRole('listitem', { name: 'donation-message' });

    const moreButton = screen.queryByRole('button', { name: /더보기/i });

    expect(moreButton).not.toBeInTheDocument();
  });

  test('창작자 본인이면 더보기 버튼이 보인다', async () => {
    myRender(<DonationMessageList isAdmin={true} />);

    await screen.findAllByRole('listitem', { name: 'donation-message' });

    await screen.findAllByRole('button', { name: /더보기/i });
  });

  test('더보기 버튼을 누르면 도네이션 메세지 목록이 더 추가된다', async () => {
    myRender(<DonationMessageList isAdmin={true} />);

    const moreButton = await screen.findByRole('button', { name: /더보기/i });

    act(() => userEvent.click(moreButton));

    await waitFor(() => {
      const donationMessages = screen.getAllByRole('listitem', { name: 'donation-message' });

      expect(donationMessageListMock.length).toBe(donationMessages.length);
    });
  });

  test('더 불러올 메세지가 없다면 더보기 버튼은 보이지 않는다.', async () => {
    myRender(<DonationMessageList isAdmin={true} />);

    const moreButton = await screen.findByRole('button', { name: /더보기/i });

    act(() => userEvent.click(moreButton));

    await waitFor(() => {
      expect(moreButton).not.toBeInTheDocument();
    });
  });
});

describe('Profile', () => {
  test('profile에 창작자 정보가 보여진다.', async () => {
    myRender(<CreatorPage />);

    await screen.findByText(userInfoMock.nickname);
    await screen.findByText(userInfoMock.bio);

    await waitFor(async () => {
      const $profileImg = (await screen.findByRole('img', { name: 'profile' })) as HTMLImageElement;

      expect($profileImg.src).toMatch(new RegExp(`${userInfoMock.profileImage}$`));
    });
  });
});
