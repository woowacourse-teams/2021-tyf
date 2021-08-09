import Button from '../../@atom/Button/Button.styles';
import Input from '../../@atom/Input/Input.styles';
import SelectBox from '../../@atom/SelectBox/SelectBox';
import Transition from '../../@atom/Transition/Transition.styles';
import StyledOutlineButton from '../../@molecule/OutlineButton/OutlineButton.styles';
import {
  StyledSettlementAccountForm,
  InputContainer,
  SettlementAccountTitle,
  StyledSubTitle,
  StyledModal,
  UploadLabel,
  FileName,
} from './SettlementAccountForm.styles';

const SettlementAccountForm = () => {
  return (
    <StyledModal>
      <StyledSettlementAccountForm>
        <SettlementAccountTitle>정산 받을 계좌 인증하기</SettlementAccountTitle>
        <Transition>
          <InputContainer>
            <StyledSubTitle>이름</StyledSubTitle>
            <Input placeholder="이름 입력하기" />
          </InputContainer>
          <InputContainer>
            <StyledSubTitle>은행</StyledSubTitle>
            <SelectBox />
          </InputContainer>
          <InputContainer>
            <StyledSubTitle>계좌번호</StyledSubTitle>
            <Input placeholder="계좌번호 입력하기" />
          </InputContainer>
          <InputContainer>
            <StyledSubTitle>통장사본</StyledSubTitle>

            <FileName>xxx.jpg</FileName>
            <UploadLabel htmlFor="upload">
              <StyledOutlineButton>파일 올리기</StyledOutlineButton>
              <input type="file"></input>
            </UploadLabel>
          </InputContainer>
        </Transition>
        <Transition delay={0.2}>
          <Button>제출하기</Button>
        </Transition>
      </StyledSettlementAccountForm>
    </StyledModal>
  );
};

export default SettlementAccountForm;
