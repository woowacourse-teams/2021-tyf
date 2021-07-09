import styled from 'styled-components';

import PALETTE from '../../constants/palette';
import { MIN_WIDTH } from '../../constants/style';

export const StyledFooter = styled.footer`
  background-color: ${PALETTE.GRAY_100};
  padding: 1rem;
  font-size: 0.75rem;
  min-width: ${MIN_WIDTH};

  p:nth-of-type(1) {
    margin-bottom: 1rem;
  }

  p:nth-of-type(2) {
    margin-bottom: 2rem;
  }
`;
