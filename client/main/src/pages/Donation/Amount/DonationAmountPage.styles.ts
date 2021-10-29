import { Link } from 'react-router-dom';
import styled, { css } from 'styled-components';

import Template from '../../../components/@atom/Template/Template';
import PALETTE from '../../../constants/palette';
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
  align-items: center;
  padding-top: 3rem;

  section:nth-of-type(1) {
    width: 100%;
    margin-bottom: 5rem;
    margin-right: 2rem;
    display: flex;
    justify-content: flex-end;
  }

  section:nth-of-type(2) {
    margin-bottom: 5rem;
  }
`;

export const PointAnchor = styled(Link)`
  font-weight: 600;
  font-size: 1.125rem;
  color: ${PALETTE.BLACK_400};

  &:active,
  &:hover {
    color: ${PALETTE.BLACK_400};
  }
`;
