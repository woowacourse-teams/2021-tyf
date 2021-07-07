import { HTMLAttributes, FC } from 'react';
import { StyledContainer } from './Container.styles';

export interface ContainerProps extends HTMLAttributes<HTMLElement> {
  children: React.ReactNode;
}

const Container: FC<ContainerProps> = ({ children, ...props }) => {
  return <StyledContainer {...props}>{children}</StyledContainer>;
};

export default Container;
