import { ChangeEvent } from 'react';
import { BANK_LIST } from '../../../constants/bank';

import useSettlementAccountForm from '../../../service/hooks/settlement/useSettlementAccountForm';
import { requestRegisterBankAccount } from '../../../service/request/settlement';
import { SettlementAccountForm } from '../../../types';
import Button from '../../@atom/Button/Button.styles';
import Input from '../../@atom/Input/Input.styles';
import SelectBox from '../../@atom/Select/Select';
import {
  StyledSettlementAccountForm,
  InputContainer,
  SettlementAccountTitle,
  StyledSubTitle,
  StyledModal,
  UploadLabel,
  FileName,
  UploadLabelButton,
} from './SettlementAccount.styles';

export interface SettlementAccountProps {
  onClose: () => void;
}

const SettlementAccount = ({ onClose }: SettlementAccountProps) => {
  const { form, setName, setBank, setAccountNumber, setBankAccountImage, isFormValid } =
    useSettlementAccountForm();

  const onChangeBankAccountImg = ({ target }: ChangeEvent<HTMLInputElement>) => {
    if (!target.files) return;
    setBankAccountImage(target.files[0]);
  };

  const registerBankAccount = async (form: SettlementAccountForm) => {
    try {
      await requestRegisterBankAccount(form);

      alert('계정정보 등록에 성공했습니다!');
    } catch (error) {
      alert('계좌정보를 등록하는데 실패했습니다.');
    }
  };

  const onSubmit = async (e: ChangeEvent<HTMLFormElement>) => {
    e.preventDefault();

    await registerBankAccount(form);
    onClose();
  };

  return (
    <StyledModal onClose={onClose}>
      <StyledSettlementAccountForm onSubmit={onSubmit}>
        <SettlementAccountTitle>정산 받을 계좌 인증하기</SettlementAccountTitle>

        <InputContainer>
          <StyledSubTitle>이름</StyledSubTitle>
          <Input
            placeholder="이름 입력하기"
            value={form.name}
            onChange={({ target }) => setName(target.value)}
          />
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
          <FileName>{form.bankAccountImage?.name || '선택된 이미지가 없습니다.'}</FileName>
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
    </StyledModal>
  );
};

export default SettlementAccount;
