import { FC } from 'react';
import { LinkProps } from 'react-router-dom';

import { StyledAnchor } from './Anchor.styles';

export interface AnchorProps extends LinkProps {
  to: string;
}

const Anchor: FC<AnchorProps> = ({ children, ...props }) => {
  return <StyledAnchor {...props}>{children}</StyledAnchor>;
};

export default Anchor;
