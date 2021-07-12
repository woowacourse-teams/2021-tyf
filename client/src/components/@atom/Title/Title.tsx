import { FC, HTMLAttributes } from 'react';

import { StyledTitle } from './Title.styles';

export type TitleProps = HTMLAttributes<HTMLHeadingElement>;

const Title: FC<TitleProps> = ({ children, ...props }) => {
  return <StyledTitle {...props}>{children}</StyledTitle>;
};

export default Title;
