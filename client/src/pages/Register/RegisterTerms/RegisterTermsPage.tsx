import { FC, HTMLAttributes } from 'react';

import Button from '../../../components/@atom/Button/Button';
import Container from '../../../components/@atom/Container/Container';
import Template from '../../../components/@atom/Template/Template';
import {
  Divider,
  RegisterTermsContainer,
  RegisterTermsTitle,
  TermCheckbox,
  TermLabel,
  TermLink,
  TermsContainer,
} from './RegisterTermsPage.styles';

const RegisterTermsPage: FC<HTMLAttributes<HTMLElement>> = () => {
  return (
    <Template>
      <RegisterTermsContainer>
        <RegisterTermsTitle>서비스 약관에 동의해 주세요.</RegisterTermsTitle>
        <TermsContainer>
          <TermLabel>
            <TermCheckbox></TermCheckbox>
            전체 동의
          </TermLabel>
          <Divider />
          <TermLabel>
            <TermCheckbox></TermCheckbox>
            <TermLink href="" target="_blank">
              서비스 약관
            </TermLink>
            에 동의 (필수)
          </TermLabel>
          <TermLabel>
            <TermCheckbox></TermCheckbox>
            <TermLink href="" target="_blank">
              개인정보 수집 및 이용
            </TermLink>
            에 동의 (필수)
          </TermLabel>
        </TermsContainer>
        <Container>
          <Button disabled>계속하기</Button>
        </Container>
      </RegisterTermsContainer>
    </Template>
  );
};

export default RegisterTermsPage;
