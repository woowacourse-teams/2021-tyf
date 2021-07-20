import { HTMLAttributes } from 'react';

import { StyledContainer } from './Container.styles';

export type ContainerProps = HTMLAttributes<HTMLElement>;

const Container = ({ children, ...props }: ContainerProps) => {
  return <StyledContainer {...props}>{children}</StyledContainer>;
};

export default Container;
