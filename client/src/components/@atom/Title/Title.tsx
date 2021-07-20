import { HTMLAttributes } from 'react';

import { StyledTitle } from './Title.styles';

export interface TitleProps extends HTMLAttributes<HTMLHeadingElement> {}

const Title = ({ children, ...props }: TitleProps) => {
  return <StyledTitle {...props}>{children}</StyledTitle>;
};

export default Title;
