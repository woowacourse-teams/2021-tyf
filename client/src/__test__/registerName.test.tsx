import { fireEvent, screen } from '@testing-library/dom';
import RegisterAddressPage from '../pages/Register/Address/RegisterAddressPage';
import { myRender } from './utils/testUtil';

describe('RegisterNamePage', () => {
  beforeEach(() => {
    myRender(<RegisterAddressPage />);
  });

  test('유효한 주소명을 입력해야 다음 버튼이 활성화 된다', () => {
    const urlNameInput = screen.getByRole('urlName');
    const nextButton = screen.getByRole('button', { name: '다음' });

    // 최초에 버튼이 비활성화 되어있다
    expect(nextButton).toBeDisabled();

    // 잘못된 주소 형식이 입력되면 에러메세지가 보여지고 버튼이 비활성화 되어있다
    fireEvent.change(urlNameInput, { target: { value: '한글은안됩니다' } });
    screen.getByText("주소는 영어 소문자, 숫자, '-', '_' 만 가능합니다.");
    expect(nextButton).toBeDisabled();

    // // 올바른 주소 형식을 입력하면 성공메세지가 보여지고 버튼이 활성화 되어있다
    fireEvent.change(urlNameInput, { target: { value: 'valid_url' } });
    screen.getByText('좋은 주소명이네요!');
    expect(nextButton).not.toBeDisabled();
  });
});
