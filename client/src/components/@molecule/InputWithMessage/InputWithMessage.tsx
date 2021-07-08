import { InputHTMLAttributes, VFC } from 'react';
import Input from '../../@atom/Input/Input';
import { FailureMessage, StyledInputWithMessage, SuccessMessage } from './InputWithMessage.styles';

export interface InputWithMessageProps extends InputHTMLAttributes<HTMLInputElement> {
  isSuccess: boolean;
  successMessage: string;
  failureMessage: string;
}

const InputWithMessage: VFC<InputWithMessageProps> = ({
  isSuccess,
  successMessage,
  failureMessage,
  value,
  ...props
}) => {
  return (
    <StyledInputWithMessage>
      <Input value={value} {...props} />
      {!!value &&
        (isSuccess ? (
          <SuccessMessage>{successMessage}</SuccessMessage>
        ) : (
          <FailureMessage>{failureMessage}</FailureMessage>
        ))}
    </StyledInputWithMessage>
  );
};

export default InputWithMessage;
