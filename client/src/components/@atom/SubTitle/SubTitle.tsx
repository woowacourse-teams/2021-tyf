import { FC, HTMLAttributes } from 'react';
import { StyledSubTitle } from './SubTitle.styles';

export type SubTitleProps = HTMLAttributes<HTMLHeadingElement>;

const SubTitle: FC<SubTitleProps> = ({ children, ...props }) => {
  return <StyledSubTitle {...props}>{children}</StyledSubTitle>;
};

export default SubTitle;
