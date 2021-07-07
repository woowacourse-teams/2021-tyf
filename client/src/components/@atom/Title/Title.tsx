import { FC, HTMLAttributes } from 'react';
import { StyledTitle } from './Title.styles';

export interface TitleProps extends HTMLAttributes<HTMLHeadingElement> {
  children: string;
}

const Title: FC<TitleProps> = ({ children, ...props }) => {
  return <StyledTitle {...props}>{children}</StyledTitle>;
};

export default Title;
