import styled, { css } from 'styled-components';

import Template from '../../../components/@atom/Template/Template';

export const popupStyle = css`
  position: absolute;
  width: 100vw;
  max-width: 100%;
  z-index: 99999;
  background: white;
  top: 0;
`;

export const DonationPageTemplate = styled(Template)`
  ${popupStyle}

  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  padding-top: 6rem;

  position section:nth-of-type(1) {
    margin-bottom: 2rem;
  }
`;

export const DonationProfile = styled.section`
  margin: 5rem 0;
`;
