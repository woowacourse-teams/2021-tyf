import styled from 'styled-components';
import { Link } from 'react-router-dom';

import PALETTE from '../../../constants/palette';

export const StyledAnchor = styled(Link)`
  font-weight: 700;
  color: ${PALETTE.GRAY_500};
  cursor: pointer;
`;
