import { VFC, InputHTMLAttributes } from 'react';

import StyledInput from './Input.styles';

export type InputProps = InputHTMLAttributes<HTMLInputElement>;

const Input: VFC<InputProps> = (props) => {
  return <StyledInput {...props} />;
};

export default Input;
