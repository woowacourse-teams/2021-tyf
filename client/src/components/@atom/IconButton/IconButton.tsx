import { ButtonHTMLAttributes, ImgHTMLAttributes, VFC } from 'react';

import { Icon, StyledIconButton } from './IconButton.styles';

export type IconButtonProps = ButtonHTMLAttributes<HTMLButtonElement> &
  Pick<ImgHTMLAttributes<HTMLImageElement>, 'src' | 'alt'>;

const IconButton = ({ src, alt = '', ...props }: IconButtonProps) => {
  return (
    <StyledIconButton {...props}>
      <Icon src={src as string} alt={alt} />
    </StyledIconButton>
  );
};

export default IconButton;
