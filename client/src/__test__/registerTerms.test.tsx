import { fireEvent, screen } from '@testing-library/react';
import { myRender } from './utils/testUtil';

import RegisterTermsPage from '../pages/Register/Terms/RegisterTermsPage';

describe('RegisterTerms', () => {
  test('필수 약관은 모두 동의해야 다음 버튼이 활성화 된다', () => {
    myRender(<RegisterTermsPage />);

    const agreeAllCheckbox = screen.getByRole('checkbox', {
      name: /전체 동의/i,
    });
    const termOfServiceCheckbox = screen.getByRole('checkbox', {
      name: /서비스 약관 에 동의 \(필수\)/i,
    });
    const personalPrivacyCheckbox = screen.getByRole('checkbox', {
      name: /개인정보 수집 및 이용 에 동의 \(필수\)/i,
    });
    const nextButton = screen.getByRole('button', { name: /계속하기/i });

    // 최초에 버튼은 비활성 되어있다.
    expect(nextButton).toBeDisabled();

    // 전체 동의를 선택하면 아래 약관들이 전체 선택 된다
    expect(termOfServiceCheckbox).not.toBeChecked();
    expect(personalPrivacyCheckbox).not.toBeChecked();

    fireEvent.click(agreeAllCheckbox);

    expect(termOfServiceCheckbox).toBeChecked();
    expect(personalPrivacyCheckbox).toBeChecked();

    // 전체 동의가 되면 버튼이 활성화 된다
    expect(nextButton).not.toBeDisabled();
  });
});
