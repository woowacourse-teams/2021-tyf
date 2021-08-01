import { LinkProps } from 'react-router-dom';

import { StyledAnchor } from './Anchor.styles';

export interface AnchorProps extends LinkProps {
  to: string;
}

const Anchor = ({ children, ...props }: AnchorProps) => {
  return <StyledAnchor {...props}>{children}</StyledAnchor>;
};

export default Anchor;
