import { fireEvent, screen } from '@testing-library/dom';
import RegisterNamePage from '../pages/Register/RegisterName/RegisterNamePage';
import { myRender } from './utils/testUtil';

describe('회원가입 닉네임 생성 페이지 테스트', () => {
  beforeEach(() => {
    myRender(<RegisterNamePage />);
  });

  test('유효한 닉네임을 입력해야 다음 버튼이 활성화 된다', () => {
    const nickNameInput = screen.getByRole('nickName');
    const nextButton = screen.getByRole('button', { name: '회원가입 완료' });

    // 최초에 버튼이 비활성화 되어있다
    expect(nextButton).toBeDisabled();

    // 잘못된 닉네임 형식이 입력되면 에러메세지가 보여지고 버튼이 비활성화 되어있다
    fireEvent.change(nickNameInput, { target: { value: '인치' } });
    screen.getByText('닉네임은 최소 3글자 이상이여합니다.');
    expect(nextButton).toBeDisabled();

    // 올바른 닉네임 형식을 입력하면 성공메세지가 보여지고 버튼이 활성화 되어있다
    fireEvent.change(nickNameInput, { target: { value: '지존인치' } });
    screen.getByText('좋은 닉네임이네요!');
    expect(nextButton).not.toBeDisabled();
  });
});
