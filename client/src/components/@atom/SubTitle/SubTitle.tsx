import { FC, HTMLAttributes } from 'react';
import { StyledSubTitle } from './SubTitle.styles';

export type SubTitleProps = HTMLAttributes<HTMLHeadingElement>;

const Title: FC<SubTitleProps> = ({ children, ...props }) => {
  return <StyledSubTitle {...props}>{children}</StyledSubTitle>;
};

export default Title;
