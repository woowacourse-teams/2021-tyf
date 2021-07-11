import Button from '../../@atom/Button/Button';
import Checkbox from '../../@atom/Checkbox/Checkbox';
import Textarea from '../../@atom/Textarea/Textarea';
import {
  StyledMessageForm,
  StyledSubTitle,
  NickNameInput,
  TextareaControllerContainer,
  CheckboxLabel,
  SubmitButton,
} from './MessageForm.styles';

const MessageForm = () => {
  return (
    <StyledMessageForm>
      <StyledSubTitle>창작자에게 응원의 한마디를 남겨주세요!</StyledSubTitle>
      <NickNameInput placeholder="닉네임 입력하기" />
      <Textarea placeholder="응원메세지 작성하기" />
      <TextareaControllerContainer>
        <CheckboxLabel>
          <Checkbox /> 창작자에게만 보이기
        </CheckboxLabel>
        <span>( 0 / 30 ) 자</span>
      </TextareaControllerContainer>
      <SubmitButton>메세지 남기기</SubmitButton>
    </StyledMessageForm>
  );
};

export default MessageForm;
