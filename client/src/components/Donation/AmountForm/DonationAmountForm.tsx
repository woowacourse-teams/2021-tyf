import { FormEvent } from 'react';
import { useHistory } from 'react-router-dom';

import useDonation from '../../../service/hooks/useDonation';
import useDonationAmountForm from '../../../service/hooks/useDonationAmountForm';
import { CreatorId } from '../../../types';
import { toCommaSeparatedString } from '../../../utils/format';
import Button from '../../@atom/Button/Button';
import SubTitle from '../../@atom/SubTitle/SubTitle.styles';
import {
  ButtonContainer,
  InputLabel,
  MoneyAddButton,
  MoneyInput,
  StyledDonationAmountForm,
} from './DonationAmountForm.styles';

export interface DonationAmountFormProps {
  creatorId: CreatorId;
}

const DonationAmountForm = ({ creatorId }: DonationAmountFormProps) => {
  const history = useHistory();
  const { donationAmount, addDonationAmount, setDonationAmount, isDonationAmountInValidRange } =
    useDonationAmountForm();
  const { donate } = useDonation(creatorId);

  const onDonate = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    // donate(Number(donationAmount));

    history.push(`/donation/${creatorId}/donatorInfo`);
  };

  return (
    <StyledDonationAmountForm onSubmit={onDonate}>
      <SubTitle>후원할 금액을 입력해주세요! 🎉</SubTitle>
      <InputLabel>
        <MoneyInput
          placeholder="0"
          value={donationAmount}
          onChange={({ target }) => setDonationAmount(target.value)}
        />
      </InputLabel>
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
