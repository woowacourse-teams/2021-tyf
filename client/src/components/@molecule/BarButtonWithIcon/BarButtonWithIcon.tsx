import { ButtonHTMLAttributes, ImgHTMLAttributes, VFC } from 'react';

import { ButtonContent, ButtonIcon, StyledBarButtonWithIcon } from './BarButtonWithIcon.styles';

export type BarButtonWithIconProps = ButtonHTMLAttributes<HTMLButtonElement> &
  ImgHTMLAttributes<HTMLImageElement>;

const BarButtonWithIcon: VFC<BarButtonWithIconProps> = ({ src, alt, children, ...args }) => {
  return (
    <StyledBarButtonWithIcon {...args}>
      <ButtonIcon src={src} alt={alt} />
      <ButtonContent>{children}</ButtonContent>
    </StyledBarButtonWithIcon>
  );
};

export default BarButtonWithIcon;
