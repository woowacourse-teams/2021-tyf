import Anchor from '../../@atom/Anchor/Anchor';
import Checkbox from '../../@atom/Checkbox/Checkbox';
import Textarea from '../../@atom/Textarea/Textarea';
import {
  StyledMessageForm,
  DonationMessageTitle,
  NickNameInput,
  TextareaControllerContainer,
  CheckboxLabel,
  SubmitButton,
} from './DonationMessageForm.styles';

const DonationMessageForm = () => {
  return (
    <StyledMessageForm>
      <DonationMessageTitle>
        창작자에게
        <br /> 응원의 한마디를
        <br /> 남겨주세요!
      </DonationMessageTitle>
      <NickNameInput placeholder="닉네임 입력하기" />
      <Textarea placeholder="응원메세지 작성하기" />
      <TextareaControllerContainer>
        <CheckboxLabel>
          <Checkbox /> 창작자에게만 보이기
        </CheckboxLabel>
        <span>( 0 / 30 ) 자</span>
      </TextareaControllerContainer>
      <SubmitButton>
        <Anchor to="/donation/success">메세지 남기기</Anchor>
      </SubmitButton>
    </StyledMessageForm>
  );
};

export default DonationMessageForm;
