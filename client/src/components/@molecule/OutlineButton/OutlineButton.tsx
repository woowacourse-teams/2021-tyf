import { FC } from 'react';

import { ButtonProps } from '../../@atom/Button/Button';
import StyledOutlineButton from './OutlineButton.styles';

const OutlineButton: FC<ButtonProps> = ({ children, ...props }) => {
  return <StyledOutlineButton {...props}>{children}</StyledOutlineButton>;
};

export default OutlineButton;
