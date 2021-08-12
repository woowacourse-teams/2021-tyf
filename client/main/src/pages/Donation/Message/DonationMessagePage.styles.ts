import styled from 'styled-components';

import Template from '../../../components/@atom/Template/Template';
import { popupStyle } from '../Amount/DonationAmountPage.styles';

export const StyledTemplate = styled(Template)`
  ${popupStyle}
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding-top: 5rem;
  margin: 0 auto;

  section:nth-of-type(1) {
    width: 100%;
    margin-bottom: 1.5rem;
  }
`;
