import { useState, VFC } from 'react';
import { useHistory } from 'react-router-dom';

import Button from '../../@atom/Button/Button';
import Container from '../../@atom/Container/Container';
import Modal from '../../@atom/Modal/Modal';
import {
  Divider,
  RegisterTermsTitle,
  TermCheckbox,
  TermLabel,
  TermLink,
  TermsContainer,
} from './RegisterTermsForm.styles';

const RegisterTermsForm: VFC = () => {
  const history = useHistory();
  const [isTermsOfServiceModalOpen, setIsTermsOfServiceModalOpen] = useState(false);
  const [isPersonalInformationUsageOpen, setIsPersonalInformationUsageOpen] = useState(false);
  const [termsChecked, setTermsChecked] = useState({
    termsOfService: false,
    personalInformationUsage: false,
  });

  const toggleTermChecked = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, checked } = event.target;

    setTermsChecked({ ...termsChecked, [name]: checked });
  };

  const toggleAllTermsChecked = (event: React.ChangeEvent<HTMLInputElement>) => {
    const toggleAll = Object.keys(termsChecked).reduce((acc, term) => {
      return Object.assign(acc, { [term]: event.target.checked });
    }, {} as typeof termsChecked);

    setTermsChecked(toggleAll);
  };

  const movePage = () => {
    history.push('/register/auth');
  };

  const isAllTermsChecked = Object.values(termsChecked).every((isChecked) => isChecked === true);
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
            onChange={toggleTermChecked}
          ></TermCheckbox>
          <TermLink onClick={() => setIsTermsOfServiceModalOpen(true)}>서비스 약관</TermLink>에 동의
          (필수)
        </TermLabel>
        <TermLabel>
          <TermCheckbox
            name="personalInformationUsage"
            checked={termsChecked['personalInformationUsage']}
            onChange={toggleTermChecked}
          ></TermCheckbox>
          <TermLink onClick={() => setIsPersonalInformationUsageOpen(true)}>
            개인정보 수집 및 이용
          </TermLink>
          에 동의 (필수)
        </TermLabel>
      </TermsContainer>
      <Container>
        <Button disabled={!isAllTermsChecked} onClick={movePage}>
          계속하기
        </Button>
      </Container>
      {isTermsOfServiceModalOpen && <Modal>서비스 이용약관</Modal>}
      {isPersonalInformationUsageOpen && <Modal>개인정보 수집 및 이용</Modal>}
    </>
  );
};

export default RegisterTermsForm;
