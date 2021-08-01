import { ButtonHTMLAttributes } from 'react';

import StyledButton from './Button.styles';

export type ButtonProps = ButtonHTMLAttributes<HTMLButtonElement>;

const Button = ({ children, ...props }: ButtonProps) => {
  return <StyledButton {...props}>{children}</StyledButton>;
};

export default Button;
