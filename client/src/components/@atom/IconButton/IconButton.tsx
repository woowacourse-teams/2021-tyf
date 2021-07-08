import { ButtonHTMLAttributes, VFC } from 'react';

import { Icon, StyledIconButton } from './IconButton.styles';

export interface IconButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  src: string;
  alt?: string;
}

const IconButton: VFC<IconButtonProps> = ({ src, alt = '', ...props }) => {
  return (
    <StyledIconButton {...props}>
      <Icon src={src} alt={alt} />
    </StyledIconButton>
  );
};

export default IconButton;
