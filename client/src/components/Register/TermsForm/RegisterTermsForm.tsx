import { useHistory } from 'react-router-dom';
import useRegisterOauthEffect from '../../../service/auth/useRegisterOauthEffect';

import useTerms from '../../../service/auth/useTerms';
import { popupTerms } from '../../../service/@utils/popupTerms';
import Button from '../../@atom/Button/Button.styles';
import {
  Divider,
  RegisterTermsTitle,
  StyledRegisterTermsForm,
  TermCheckbox,
  TermLabel,
  TermLink,
  TermsButtonContainer,
  TermsContainer,
} from './RegisterTermsForm.styles';

const RegisterTermsForm = () => {
  const history = useHistory();
  const { termsChecked, isAllTermsChecked, toggleTermChecked, toggleAllTermsChecked } = useTerms();

  const routeToRegisterPageNamePage = () => {
    history.push('/register/url');
  };

  useRegisterOauthEffect();

  return (
    <StyledRegisterTermsForm>
      <RegisterTermsTitle>
        <span>서비스 </span>
        <span>약관에 </span>
        <span>동의해 주세요</span>
      </RegisterTermsTitle>
      <TermsContainer>
        <TermLabel>
          <TermCheckbox checked={isAllTermsChecked} onChange={toggleAllTermsChecked}></TermCheckbox>
          전체 동의
        </TermLabel>
        <Divider />
        <TermLabel>
          <TermCheckbox
            name="termsOfService"
            checked={termsChecked['termsOfService']}
            onChange={(e) => toggleTermChecked(e.target)}
          ></TermCheckbox>
          <TermLink onClick={() => popupTerms('/contracts/creator-policy.html')}>
            서비스 약관
          </TermLink>
          에 동의 (필수)
        </TermLabel>
        <TermLabel>
          <TermCheckbox
            name="personalInformationUsage"
            checked={termsChecked['personalInformationUsage']}
            onChange={(e) => toggleTermChecked(e.target)}
          ></TermCheckbox>
          <TermLink onClick={() => popupTerms('/contracts/privacy.html')}>
            개인정보 수집 및 이용
          </TermLink>
          에 동의 (필수)
        </TermLabel>
      </TermsContainer>
      <TermsButtonContainer>
        <Button disabled={!isAllTermsChecked} onClick={routeToRegisterPageNamePage}>
          계속하기
        </Button>
      </TermsButtonContainer>
    </StyledRegisterTermsForm>
  );
};

export default RegisterTermsForm;
