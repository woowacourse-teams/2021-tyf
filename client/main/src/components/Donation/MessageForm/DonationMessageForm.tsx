import { FormEvent } from 'react';

import { MAX_MESSAGE_LENGTH } from '../../../constants/donation';
import useDonationMessage from '../../../service//donation/useDonationMessage';
import useDonationMessageForm from '../../../service//donation/useDonationMessageForm';
import { CreatorId } from '../../../types';
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

export interface DonationMessageFormProps {
  creatorId: CreatorId;
}

const DonationMessageForm = ({ creatorId }: DonationMessageFormProps) => {
  const { form, isPrivate, setMessage, setName, setIsPrivate } = useDonationMessageForm();
  const { sendDonationMessage } = useDonationMessage(creatorId);

  const onSubmitMessage = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    sendDonationMessage(form.name, form.message, isPrivate);
  };

  return (
    <StyledMessageForm onSubmit={onSubmitMessage}>
      <DonationMessageTitle>
        창작자에게
        <br /> 응원의 한마디를
        <br /> 남겨주세요!
      </DonationMessageTitle>
      <NickNameInput
        role="nickname-input"
        placeholder="닉네임 입력하기"
        value={form.name}
        onChange={({ target }) => setName(target.value)}
      />
      <Textarea
        role="message-input"
        placeholder="응원메세지 작성하기"
        value={form.message}
        onChange={({ target }) => setMessage(target.value)}
      />
      <TextareaControllerContainer>
        <CheckboxLabel>
          <Checkbox checked={isPrivate} onChange={({ target }) => setIsPrivate(target.checked)} />
          창작자에게만 보이기
        </CheckboxLabel>
        <span>
          ( {form.message.length} / {MAX_MESSAGE_LENGTH} ) 자
        </span>
      </TextareaControllerContainer>
      <SubmitButton>메세지 남기기</SubmitButton>
    </StyledMessageForm>
  );
};

export default DonationMessageForm;
