import { ChangeEvent } from 'react';

import { BANK_LIST } from '../../../constants/bank';
import useSettlementAccountForm from '../../../service/settlement/useSettlementAccountForm';
import Button from '../../@atom/Button/Button.styles';
import Input from '../../@atom/Input/Input.styles';
import SelectBox from '../../@atom/Select/Select';
import {
  StyledSettlementAccountForm,
  InputContainer,
  SettlementAccountTitle,
  StyledSubTitle,
  UploadLabel,
  FileName,
  UploadLabelButton,
  RegistrationNumberContainer,
  RegistrationNumberInput,
  RegistrationNumberSeparator,
} from './SettlementRegisterForm.styles';
import useSettlement from '../../../service/settlement/useSettlement';

const SettlementRegisterForm = () => {
  const {
    form,
    setBank,
    setAccountNumber,
    setAccountHolder,
    setBankbookImage,
    setResidentRegistrationNumberFront,
    setResidentRegistrationNumberRear,
    isFormValid,
  } = useSettlementAccountForm();

  const { registerSettlementAccount } = useSettlement();

  const onChangeBankAccountImg = ({ target }: ChangeEvent<HTMLInputElement>) => {
    if (!target.files) return;

    setBankbookImage(target.files[0]);
  };

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
      <InputContainer>
        <StyledSubTitle>통장사본</StyledSubTitle>
        <FileName>{form.bankbookImage?.name ?? '선택된 이미지가 없습니다.'}</FileName>
        <UploadLabel>
          <input
            type="file"
            onChange={onChangeBankAccountImg}
            accept="image/png, image/jpeg, image/jpg"
            hidden
          />
          <UploadLabelButton type="button">파일 올리기</UploadLabelButton>
        </UploadLabel>
      </InputContainer>

      <Button disabled={!isFormValid}>제출하기</Button>
    </StyledSettlementAccountForm>
  );
};

export default SettlementRegisterForm;
