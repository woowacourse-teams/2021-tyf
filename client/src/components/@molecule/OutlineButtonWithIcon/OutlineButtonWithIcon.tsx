import { ButtonHTMLAttributes, ImgHTMLAttributes, VFC } from 'react';
import {
  ButtonContent,
  ButtonIcon,
  StyledOutlineButtonWithIcon,
} from './OutlineButtonWithIcon.styles';

type OutlineButtonWithIconProps = ButtonHTMLAttributes<HTMLButtonElement> &
  ImgHTMLAttributes<HTMLImageElement>;

const OutlineButtonWithIcon: VFC<OutlineButtonWithIconProps> = ({
  src,
  alt,
  children,
  ...args
}) => {
  return (
    <StyledOutlineButtonWithIcon {...args}>
      <ButtonIcon src={src} alt={alt} />
      <ButtonContent>{children}</ButtonContent>
    </StyledOutlineButtonWithIcon>
  );
};

export default OutlineButtonWithIcon;
