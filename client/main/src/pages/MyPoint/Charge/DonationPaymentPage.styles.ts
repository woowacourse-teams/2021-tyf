import styled from 'styled-components';

import Container from '../../../components/@atom/Container/Container.styles';
import SubTitle from '../../../components/@atom/SubTitle/SubTitle.styles';
import Template from '../../../components/@atom/Template/Template';
import { SIZE } from '../../../constants/device';
import { popupStyle } from '../../Donation/Amount/DonationAmountPage.styles';

export const DonationPaymentPageTemplate = styled(Template)`
  ${popupStyle}

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding-top: 5rem;

  section:nth-of-type(1) {
    margin-bottom: 3rem;
  }
`;

export const StyledSubTitle = styled(SubTitle)`
  margin-bottom: 4rem;
`;

export const PaymentButtonContainer = styled(Container)`
  max-width: ${SIZE.MOBILE_MAX}px;

  button {
    margin-bottom: 1rem;
  }
`;
