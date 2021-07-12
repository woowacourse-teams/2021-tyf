import styled from 'styled-components';

import Template from '../../../components/@atom/Template/Template';

export const DonationPageTemplate = styled(Template)`
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;

  section:nth-of-type(1) {
    margin-bottom: 2rem;
  }
`;
