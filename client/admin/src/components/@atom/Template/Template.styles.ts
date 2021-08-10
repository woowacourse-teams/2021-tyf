import styled from 'styled-components';
import { DEVICE, SIZE } from '../../../constants/device';

export const StyledTemplate = styled.main``;

export const InnerTemplate = styled.section`
  min-width: ${SIZE.MOBILE_MIN}px;
  padding: 5rem 3rem;
  width: calc(100% - 6rem);
  max-width: ${SIZE.MOBILE_MAX}px;
  min-height: calc(100vh);
  margin: 0 auto;

  @media ${DEVICE.DESKTOP} {
    max-width: ${SIZE.DESKTOP_LARGE}px;
  }
`;
