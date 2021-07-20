import { ButtonHTMLAttributes, ImgHTMLAttributes } from 'react';

import { ButtonContent, ButtonIcon, StyledIconBarButton } from './IconBarButton.styles';

export type IconBarButtonProps = ButtonHTMLAttributes<HTMLButtonElement> &
  ImgHTMLAttributes<HTMLImageElement>;

const IconBarButton = ({ src, alt, children, ...args }: IconBarButtonProps) => {
  return (
    <StyledIconBarButton {...args}>
      <ButtonIcon src={src} alt={alt} />
      <ButtonContent>{children}</ButtonContent>
    </StyledIconBarButton>
  );
};

export default IconBarButton;
