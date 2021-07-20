import { HTMLAttributes } from 'react';

import { StyledSubTitle } from './SubTitle.styles';

export interface SubTitleProps extends HTMLAttributes<HTMLHeadingElement> {}

const SubTitle = ({ children, ...props }: SubTitleProps) => {
  return <StyledSubTitle {...props}>{children}</StyledSubTitle>;
};

export default SubTitle;
