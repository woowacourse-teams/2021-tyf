import { screen, waitFor } from '@testing-library/dom';
import userEvent from '@testing-library/user-event';

import LoginPage from '../pages/LoginPage';
import { myRender } from './utils/testUtil';

const historyPushMock = jest.fn();
jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom'),
  useHistory: () => ({
    push: historyPushMock,
  }),
}));

const alertMock = jest.fn();
window.alert = alertMock;

describe('LoginPage', () => {
  beforeEach(() => {
    myRender(<LoginPage />);
  });

  test('아이디와 비밀번호를 입력해, 로그인할 수 있다.', () => {
    const $idInput = screen.getByPlaceholderText('아이디');
    const $passwordInput = screen.getByPlaceholderText('비밀번호');
    const $loginButton = screen.getByRole('button', { name: '로그인' });

    userEvent.type($idInput, 'id');
    userEvent.type($passwordInput, 'pwd');
    userEvent.click($loginButton);

    waitFor(() => {
      expect(alertMock).toBeCalled();
      expect(historyPushMock).toBeCalled();
    });
  });
});
