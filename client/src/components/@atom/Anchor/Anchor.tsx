import { AnchorHTMLAttributes, FC } from 'react';
import { StyledAnchor } from './Anchor.styles';

export interface AnchorProps extends AnchorHTMLAttributes<HTMLAnchorElement> {
  children: string;
}

const Anchor: FC<AnchorProps> = ({ children, ...props }) => {
  return <StyledAnchor {...props}>{children}</StyledAnchor>;
};

export default Anchor;
