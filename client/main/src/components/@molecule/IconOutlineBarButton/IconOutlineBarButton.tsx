import { ButtonHTMLAttributes, ImgHTMLAttributes } from 'react';

import {
  ButtonContent,
  ButtonIcon,
  StyledIconOutlineBarButton,
} from './IconOutlineBarButton.styles';

export type IconOutlineBarButtonProps = ButtonHTMLAttributes<HTMLButtonElement> &
  ImgHTMLAttributes<HTMLImageElement>;

const IconOutlineBarButton = ({ src, alt, children, ...args }: IconOutlineBarButtonProps) => {
  return (
    <StyledIconOutlineBarButton {...args}>
      <ButtonIcon src={src} alt={alt} />
      <ButtonContent>{children}</ButtonContent>
    </StyledIconOutlineBarButton>
  );
};

export default IconOutlineBarButton;
