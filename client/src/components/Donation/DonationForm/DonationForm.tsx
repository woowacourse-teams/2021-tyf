import Anchor from '../../@atom/Anchor/Anchor';
import Button from '../../@atom/Button/Button';
import SubTitle from '../../@atom/SubTitle/SubTitle';
import {
  ButtonContainer,
  InputLabel,
  MoneyAddButton,
  MoneyInput,
  StyledDonationForm,
} from './DonationForm.styles';

const DonationForm = () => {
  return (
    <StyledDonationForm>
      <SubTitle>후원할 금액을 입력해주세요! 🎉</SubTitle>
      <InputLabel>
        <MoneyInput placeholder="0" />
      </InputLabel>
      <ButtonContainer>
        <MoneyAddButton>+1,000원</MoneyAddButton>
        <MoneyAddButton>+2,000원</MoneyAddButton>
        <MoneyAddButton>+3,000원</MoneyAddButton>
      </ButtonContainer>
      <Button>
        <Anchor to="/donation/message">후원하기</Anchor>
      </Button>
    </StyledDonationForm>
  );
};

export default DonationForm;
