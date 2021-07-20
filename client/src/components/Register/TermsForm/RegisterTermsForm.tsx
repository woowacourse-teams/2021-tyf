import { useHistory } from 'react-router-dom';

import useTerms from '../../../service/hooks/useTerms';
import Button from '../../@atom/Button/Button';
import Container from '../../@atom/Container/Container';
import {
  Divider,
  RegisterTermsTitle,
  TermCheckbox,
  TermLabel,
  TermLink,
  TermsContainer,
} from './RegisterTermsForm.styles';

const RegisterTermsForm = () => {
  const history = useHistory();
  const { termsChecked, isAllTermsChecked, toggleTermChecked, toggleAllTermsChecked } = useTerms();

  const routeToRegisterAuthPage = () => {
    // TODO: route명들 다 상수화하기
    history.push('/register/auth');
  };

  return (
    <>
      <RegisterTermsTitle>
        서비스
        <br /> 약관에
        <br /> 동의해 주세요.
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
          <TermLink href="/" target="_blank">
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
          <TermLink href="/" target="_blank">
            개인정보 수집 및 이용
          </TermLink>
          에 동의 (필수)
        </TermLabel>
      </TermsContainer>
      <Container>
        <Button disabled={!isAllTermsChecked} onClick={routeToRegisterAuthPage}>
          계속하기
        </Button>
      </Container>
    </>
  );
};

export default RegisterTermsForm;
