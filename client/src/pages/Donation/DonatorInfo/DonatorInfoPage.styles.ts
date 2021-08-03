import styled from 'styled-components';

import Template from '../../../components/@atom/Template/Template';
import { popupStyle } from '../Amount/DonationAmountPage.styles';

export const DonatorInfoPageTemplate = styled(Template)`
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
