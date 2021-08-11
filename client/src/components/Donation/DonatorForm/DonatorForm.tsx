import { useHistory } from 'react-router-dom';

import useDonatorForm from '../../../service//donation/useDonatorForm';
import { popupTerms } from '../../../service/@utils/popupTerms';
import { CreatorId } from '../../../types';
import Button from '../../@atom/Button/Button.styles';
import SubTitle from '../../@atom/SubTitle/SubTitle.styles';
import ValidationInput from '../../@molecule/ValidationInput/ValidationInput';
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
  const { form, isTermChecked, isValidEmail, setEmail, setIsTermChecked } = useDonatorForm();

  const routeToPaymentPage = () => {
    history.push(`/donation/${creatorId}/payment`);
  };

  return (
    <StyledDonatorForm onSubmit={routeToPaymentPage}>
      <SubTitle>후원자님의 정보를 입력해주세요!</SubTitle>

      <ValidationInput
        type="email"
        role="email-input"
        placeholder="이메일 입력하기"
        value={form.email}
        onChange={({ target }) => setEmail(target.value)}
        isSuccess={isValidEmail}
        successMessage="올바른 이메일 형식입니다!"
        failureMessage="잘못된 이메일 형식입니다."
      />
      <InputContainer>
        <TermLabel>
          <TermCheckbox
            name="termsOfService"
            checked={isTermChecked}
            onChange={({ target }) => setIsTermChecked(target.checked)}
          ></TermCheckbox>
          <TermLink onClick={() => popupTerms('/contracts/donator-policy.html')}>
            결제 약관
          </TermLink>
          에 동의 (필수)
        </TermLabel>

        <Button disabled={!(isValidEmail && isTermChecked)}>다음</Button>
      </InputContainer>
    </StyledDonatorForm>
  );
};

export default DonatorForm;
