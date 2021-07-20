import { InputHTMLAttributes, forwardRef } from 'react';

import StyledInput from './Input.styles';

export interface InputProps extends InputHTMLAttributes<HTMLInputElement> {}

const Input = forwardRef<HTMLInputElement, InputProps>((props, ref) => {
  return <StyledInput ref={ref} {...props} />;
});

Input.displayName = 'Input';

export default Input;
