import { ButtonHTMLAttributes, ImgHTMLAttributes, VFC } from 'react';
import { ButtonContent, ButtonIcon, StyledButtonWithIcon } from './ButtonWithIcon.styles';

type ButtonWithIconProps = ButtonHTMLAttributes<HTMLButtonElement> &
  ImgHTMLAttributes<HTMLImageElement>;

const ButtonWithIcon: VFC<ButtonWithIconProps> = ({ src, alt, children, ...args }) => {
  return (
    <StyledButtonWithIcon {...args}>
      <ButtonIcon src={src} alt={alt} />
      <ButtonContent>{children}</ButtonContent>
    </StyledButtonWithIcon>
  );
};

export default ButtonWithIcon;
