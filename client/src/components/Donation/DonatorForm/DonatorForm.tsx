import { useState } from 'react';
import { useHistory } from 'react-router-dom';

import { CreatorId } from '../../../types';
import Button from '../../@atom/Button/Button';
import Input from '../../@atom/Input/Input';
import SubTitle from '../../@atom/SubTitle/SubTitle.styles';
import {
  StyledDonatorForm,
  InputContainer,
  TermLabel,
  TermCheckbox,
  TermLink,
} from './DonatorForm.styles';

export interface DonatorFormProps {
  creatorId: CreatorId;
}

const DonatorForm = ({ creatorId }: DonatorFormProps) => {
  const history = useHistory();
  const [email, setEmail] = useState('');

  const routeToPaymentPage = () => {
    history.push(`/donation/${creatorId}/payment`);
  };

  return (
    <StyledDonatorForm onSubmit={routeToPaymentPage}>
      <SubTitle>후원자님의 정보를 입력해주세요!</SubTitle>

      <Input
        placeholder="이메일 입력하기"
        value={email}
        onChange={({ target }) => setEmail(target.value)}
      />
      <InputContainer>
        <TermLabel>
          <TermCheckbox
            name="termsOfService"
            // checked={termsChecked['termsOfService']}
            // onChange={(e) => toggleTermChecked(e.target)}
          ></TermCheckbox>
          <TermLink href="/" target="_blank">
            결제 약관
          </TermLink>
          에 동의 (필수)
        </TermLabel>

        <Button>다음</Button>
      </InputContainer>
    </StyledDonatorForm>
  );
};

export default DonatorForm;
