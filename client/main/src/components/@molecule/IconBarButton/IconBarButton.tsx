import { ButtonHTMLAttributes, ImgHTMLAttributes } from 'react';

import { ButtonIcon, StyledIconBarButton } from './IconBarButton.styles';

export type IconBarButtonProps = ButtonHTMLAttributes<HTMLButtonElement> &
  ImgHTMLAttributes<HTMLImageElement>;

const IconBarButton = ({ src, alt, children, ...args }: IconBarButtonProps) => {
  return (
    <StyledIconBarButton {...args}>
      <ButtonIcon src={src} alt={alt} />
      {children}
    </StyledIconBarButton>
  );
};

export default IconBarButton;
