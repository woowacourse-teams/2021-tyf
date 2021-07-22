import styled from 'styled-components';

import { DEVICE, SIZE } from '../../../constants/device';

export const StyledTemplate = styled.main`
  width: 100vw;
`;

export const InnerTemplate = styled.section`
  padding: 3rem 1rem;

  min-width: ${SIZE.MOBILE_MIN};
  width: 100%;
  max-width: ${SIZE.MOBILE_MAX};
  min-height: calc(100vh - 3.5rem);
  min-height: calc(${window.innerHeight}px - 3.5rem);
  margin: 0 auto;

  @media ${DEVICE.DESKTOP} {
    max-width: ${SIZE.DESKTOP_MAX};
  }
`;
