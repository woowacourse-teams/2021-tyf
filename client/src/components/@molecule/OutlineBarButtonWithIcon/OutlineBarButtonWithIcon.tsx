import { ButtonHTMLAttributes, ImgHTMLAttributes, VFC } from 'react';
import {
  ButtonContent,
  ButtonIcon,
  StyledOutlineBarButtonWithIcon,
} from './OutlineBarButtonWithIcon.styles';

type OutlineBarButtonWithIconProps = ButtonHTMLAttributes<HTMLButtonElement> &
  ImgHTMLAttributes<HTMLImageElement>;

const OutlineBarButtonWithIcon: VFC<OutlineBarButtonWithIconProps> = ({
  src,
  alt,
  children,
  ...args
}) => {
  return (
    <StyledOutlineBarButtonWithIcon {...args}>
      <ButtonIcon src={src} alt={alt} />
      <ButtonContent>{children}</ButtonContent>
    </StyledOutlineBarButtonWithIcon>
  );
};

export default OutlineBarButtonWithIcon;
