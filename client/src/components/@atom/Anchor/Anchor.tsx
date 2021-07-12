import { AnchorHTMLAttributes, FC } from 'react';

import { StyledAnchor } from './Anchor.styles';

export type AnchorProps = AnchorHTMLAttributes<HTMLAnchorElement>;

const Anchor: FC<AnchorProps> = ({ children, ...props }) => {
  return <StyledAnchor {...props}>{children}</StyledAnchor>;
};

export default Anchor;
