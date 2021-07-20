import styled from 'styled-components';

import Template from '../../../components/@atom/Template/Template';
import { popupStyle } from '../Donation/DonationPage.styles';

export const StyledTemplate = styled(Template)`
  ${popupStyle}
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding-top: 5rem;
`;
