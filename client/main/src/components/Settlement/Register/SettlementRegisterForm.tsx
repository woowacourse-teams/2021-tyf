import { ChangeEvent } from 'react';
import { BANK_LIST } from '../../../constants/bank';
import useSettlement from '../../../service/settlement/useSettlement';
import useSettlementAccountForm from '../../../service/settlement/useSettlementAccountForm';
import Button from '../../@atom/Button/Button.styles';
import Input from '../../@atom/Input/Input.styles';
import SelectBox from '../../@atom/Select/Select';
import {
  InputContainer,
  RegistrationNumberContainer,
  RegistrationNumberInput,
  RegistrationNumberSeparator,
  SettlementAccountTitle,
  StyledSettlementAccountForm,
  StyledSubTitle,
} from './SettlementRegisterForm.styles';

const SettlementRegisterForm = () => {
  const {
    form,
    setBank,
    setAccountNumber,
    setAccountHolder,
    setResidentRegistrationNumberFront,
    setResidentRegistrationNumberRear,
    isFormValid,
  } = useSettlementAccountForm();

  const { registerSettlementAccount } = useSettlement();

  const onSubmit = (event: ChangeEvent<HTMLFormElement>) => {
    event.preventDefault();
    if (!isFormValid) return;

    registerSettlementAccount(form);
  };

  return (
    <StyledSettlementAccountForm onSubmit={onSubmit}>
      <SettlementAccountTitle>
        <span>정산 받을 계좌</span> <span>인증하기</span>
      </SettlementAccountTitle>

      <InputContainer>
        <StyledSubTitle>이름</StyledSubTitle>
        <Input
          placeholder="이름 입력하기"
          value={form.accountHolder}
          onChange={({ target }) => setAccountHolder(target.value)}
        />
      </InputContainer>
      <InputContainer>
        <StyledSubTitle>주민등록번호</StyledSubTitle>
        <RegistrationNumberContainer>
          <RegistrationNumberInput
            value={form.residentRegistrationNumber[0]}
            onChange={({ target }) => setResidentRegistrationNumberFront(target.value)}
          />
          <RegistrationNumberSeparator>{'-'}</RegistrationNumberSeparator>
          <RegistrationNumberInput
            value={form.residentRegistrationNumber[1]}
            onChange={({ target }) => setResidentRegistrationNumberRear(target.value)}
          />
        </RegistrationNumberContainer>
      </InputContainer>
      <InputContainer>
        <StyledSubTitle>은행</StyledSubTitle>
        <SelectBox
          placeholder="은행을 선택해주세요"
          selectOptions={BANK_LIST}
          value={form.bank}
          onChange={setBank}
        />
      </InputContainer>
      <InputContainer>
        <StyledSubTitle>계좌번호</StyledSubTitle>
        <Input
          placeholder="계좌번호 입력하기"
          value={form.accountNumber}
          onChange={({ target }) => setAccountNumber(target.value)}
        />
      </InputContainer>

      <Button disabled={!isFormValid}>제출하기</Button>
    </StyledSettlementAccountForm>
  );
};

export default SettlementRegisterForm;
