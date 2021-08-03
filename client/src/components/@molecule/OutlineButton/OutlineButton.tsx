import { ButtonProps } from '../../@atom/Button/Button';
import StyledOutlineButton from './OutlineButton.styles';

const OutlineButton = ({ children, ...props }: ButtonProps) => {
  return <StyledOutlineButton {...props}>{children}</StyledOutlineButton>;
};

export default OutlineButton;
