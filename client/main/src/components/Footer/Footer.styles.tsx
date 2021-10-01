import styled from 'styled-components';

import { SIZE } from '../../constants/device';
import PALETTE from '../../constants/palette';

export const StyledFooter = styled.footer`
  background-color: ${PALETTE.GRAY_100};
  padding: 1rem;
  font-size: 0.75rem;
  min-width: ${SIZE.MOBILE_MIN}px;
  line-height: 1rem;

  p:nth-of-type(1) {
    margin-bottom: 1rem;
  }

  p:nth-of-type(2) {
    margin-bottom: 1rem;
  }

  p:nth-of-type(3) {
    margin-bottom: 2rem;
  }
`;
