import { HTMLAttributes, FC } from 'react';
import { StyledContainer } from './Container.styles';

export type ContainerProps = HTMLAttributes<HTMLElement>;

const Container: FC<ContainerProps> = ({ children, ...props }) => {
  return <StyledContainer {...props}>{children}</StyledContainer>;
};

export default Container;
