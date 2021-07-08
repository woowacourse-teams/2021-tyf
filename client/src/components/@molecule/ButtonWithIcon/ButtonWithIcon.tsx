import { ButtonHTMLAttributes, VFC } from 'react';
import { ButtonContent, ButtonIcon, StyledButtonWithIcon } from './ButtonWithIcon.styles';

interface ButtonWithIconProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  logoSrc: string;
  logoAlt: string;
}

const ButtonWithIcon: VFC<ButtonWithIconProps> = ({ logoSrc, logoAlt, children, ...args }) => {
  return (
    <StyledButtonWithIcon {...args}>
      <ButtonIcon src={logoSrc} alt={logoAlt} />
      <ButtonContent>{children}</ButtonContent>
    </StyledButtonWithIcon>
  );
};

export default ButtonWithIcon;
