import { ChangeEvent } from 'react';

import useSettlementAccountForm from '../../../service/hooks/settlement/useSettlementAccountForm';
import Button from '../../@atom/Button/Button.styles';
import Input from '../../@atom/Input/Input.styles';
import SelectBox from '../../@atom/SelectBox/SelectBox';
import {
  StyledSettlementAccountForm,
  InputContainer,
  SettlementAccountTitle,
  StyledSubTitle,
  StyledModal,
  UploadLabel,
  FileName,
  UploadLabelButton,
} from './SettlementAccountForm.styles';

const SettlementAccountForm = () => {
  const { form, setName, setBank, setAccountNumber, setBankAccountImage, isValid } =
    useSettlementAccountForm();

  const onChangeBankAccountImg = ({ target }: ChangeEvent<HTMLInputElement>) => {
    if (!target.files) return;
    setBankAccountImage(target.files[0]);
  };

  const banks = ['국민', '신한', '우리', '하나'];

  return (
    <StyledModal>
      <StyledSettlementAccountForm>
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
            selectHeader="은행을 선택해주세요"
            selectOptions={banks}
            selected={form.bank}
            setSelected={setBank}
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

        <Button disabled={!isValid}>제출하기</Button>
      </StyledSettlementAccountForm>
    </StyledModal>
  );
};

export default SettlementAccountForm;
