import { FormEvent } from 'react';

import useDonationAmountForm from '../../../service/donation/useDonationAmountForm';
import useDonation from '../../../service/donation/useDonation';
import { CreatorId } from '../../../types';
import { toCommaSeparatedString } from '../../../utils/format';
import Button from '../../@atom/Button/Button.styles';
import SubTitle from '../../@atom/SubTitle/SubTitle.styles';
import {
  ButtonContainer,
  InputLabel,
  MoneyAddButton,
  MoneyInputContainer,
  MoneyInput,
  StyledDonationAmountForm,
} from './DonationAmountForm.styles';

export interface DonationAmountFormProps {
  creatorId: CreatorId;
}

const DonationAmountForm = ({ creatorId }: DonationAmountFormProps) => {
  const { donationAmount, addDonationAmount, setDonationAmount, isDonationAmountInValidRange } =
    useDonationAmountForm();
  const { donate } = useDonation();

  const onSetDonationAmount = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    donate(creatorId, donationAmount);
  };

  return (
    <StyledDonationAmountForm onSubmit={onSetDonationAmount}>
      <SubTitle>도네이션 할 포인트를 입력해주세요! 🎉</SubTitle>
      <MoneyInputContainer>
        <InputLabel>
          <MoneyInput
            aria-label="money"
            placeholder="0"
            value={donationAmount || ''}
            onChange={({ target }) => setDonationAmount(target.value)}
          />
        </InputLabel>
      </MoneyInputContainer>
      <ButtonContainer>
        <MoneyAddButton onClick={() => addDonationAmount(1000)}>
          +{toCommaSeparatedString(1000)}tp
        </MoneyAddButton>
        <MoneyAddButton onClick={() => addDonationAmount(2000)}>
          +{toCommaSeparatedString(2000)}tp
        </MoneyAddButton>
        <MoneyAddButton onClick={() => addDonationAmount(3000)}>
          +{toCommaSeparatedString(3000)}tp
        </MoneyAddButton>
      </ButtonContainer>
      <Button disabled={!isDonationAmountInValidRange}>도네이션</Button>
    </StyledDonationAmountForm>
  );
};

export default DonationAmountForm;
