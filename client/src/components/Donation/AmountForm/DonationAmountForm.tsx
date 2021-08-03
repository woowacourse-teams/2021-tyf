import { FormEvent } from 'react';
import { useHistory } from 'react-router-dom';

import useDonationAmountForm from '../../../service/hooks/useDonationAmountForm';
import { CreatorId } from '../../../types';
import { toCommaSeparatedString } from '../../../utils/format';
import Button from '../../@atom/Button/Button';
import SubTitle from '../../@atom/SubTitle/SubTitle.styles';
import { MAX_DONATION_AMOUNT, MIN_DONATION_AMOUNT } from '../../../constants/donation';
import {
  ButtonContainer,
  InputLabel,
  MoneyAddButton,
  MoneyInputContainer,
  MoneyValidationInput,
  StyledDonationAmountForm,
} from './DonationAmountForm.styles';

export interface DonationAmountFormProps {
  creatorId: CreatorId;
}

const DonationAmountForm = ({ creatorId }: DonationAmountFormProps) => {
  const history = useHistory();
  const { donationAmount, addDonationAmount, setDonationAmount, isDonationAmountInValidRange } =
    useDonationAmountForm();

  const onDonate = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    history.push(`/donation/${creatorId}/donatorInfo`);
  };

  return (
    <StyledDonationAmountForm onSubmit={onDonate}>
      <SubTitle>후원할 금액을 입력해주세요! 🎉</SubTitle>
      <MoneyInputContainer>
        <InputLabel>
          <MoneyValidationInput
            placeholder="0"
            value={donationAmount}
            onChange={({ target }) => setDonationAmount(target.value)}
            isSuccess={isDonationAmountInValidRange}
            successMessage=""
            failureMessage={`후원 금액은 최소 ${MIN_DONATION_AMOUNT}원 이상, 최대 ${MAX_DONATION_AMOUNT}원 이하여야 합니다.`}
          />
        </InputLabel>
      </MoneyInputContainer>
      <ButtonContainer>
        <MoneyAddButton onClick={() => addDonationAmount(1000)}>
          +{toCommaSeparatedString(1000)}원
        </MoneyAddButton>
        <MoneyAddButton onClick={() => addDonationAmount(2000)}>
          +{toCommaSeparatedString(2000)}원
        </MoneyAddButton>
        <MoneyAddButton onClick={() => addDonationAmount(3000)}>
          +{toCommaSeparatedString(3000)}원
        </MoneyAddButton>
      </ButtonContainer>
      <Button disabled={!isDonationAmountInValidRange}>후원하기</Button>
    </StyledDonationAmountForm>
  );
};

export default DonationAmountForm;
