import { ButtonHTMLAttributes, VFC } from 'react';
import {
  ButtonContent,
  ButtonIcon,
  StyledOutlineButtonWithIcon,
} from './OutlineButtonWithIcon.styles';

interface OutlineButtonWithIconProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  logoSrc: string;
  logoAlt: string;
}

const OutlineButtonWithIcon: VFC<OutlineButtonWithIconProps> = ({
  logoSrc,
  logoAlt,
  children,
  ...args
}) => {
  return (
    <StyledOutlineButtonWithIcon {...args}>
      <ButtonIcon src={logoSrc} alt={logoAlt} />
      <ButtonContent>{children}</ButtonContent>
    </StyledOutlineButtonWithIcon>
  );
};

export default OutlineButtonWithIcon;
