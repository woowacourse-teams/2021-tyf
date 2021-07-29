import styled, { css } from 'styled-components';

import Template from '../../../components/@atom/Template/Template';
import { Z_INDEX } from '../../../constants/style';

export const popupStyle = css`
  position: absolute;
  width: 100vw;
  max-width: 100%;
  z-index: ${Z_INDEX.FOREGROUND};
  background: white;
  top: 0;
`;

export const DonationAmountPageTemplate = styled(Template)`
  ${popupStyle}

  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  padding-top: 5rem;

  section:nth-of-type(1) {
    margin-bottom: 3rem;
  }
`;
