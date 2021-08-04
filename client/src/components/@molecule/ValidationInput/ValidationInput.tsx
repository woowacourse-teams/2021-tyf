import { InputHTMLAttributes, VFC } from 'react';

import Input from '../../@atom/Input/Input.styles';
import { FailureMessage, StyledValidationInput, SuccessMessage } from './ValidationInput.styles';

export interface ValidationInputProps extends InputHTMLAttributes<HTMLInputElement> {
  isSuccess: boolean;
  successMessage: string;
  failureMessage: string;
}

const ValidationInput = ({
  isSuccess,
  successMessage,
  failureMessage,
  value,
  ...props
}: ValidationInputProps) => {
  return (
    <StyledValidationInput>
      <Input value={value} {...props} />
      {!!value &&
        (isSuccess ? (
          <SuccessMessage>{successMessage}</SuccessMessage>
        ) : (
          <FailureMessage>{failureMessage}</FailureMessage>
        ))}
    </StyledValidationInput>
  );
};

export default ValidationInput;
