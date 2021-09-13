import styled from 'styled-components';
import { Link } from 'react-router-dom';

import PALETTE from '../../../constants/palette';

export const StyledAnchor = styled(Link)`
  font-weight: 700;
  color: ${PALETTE.BLACK_400};
  cursor: pointer;
`;
