import styled from 'styled-components';
import { DEVICE, SIZE } from '../../../constants/device';

export const StyledTemplate = styled.main``;

export const InnerTemplate = styled.section`
  min-width: ${SIZE.DESKTOP_LARGE}px;
  padding: 5rem 3rem;
  width: calc(100% - 6rem);
  max-width: ${SIZE.DESKTOP_LARGE}px;
  min-height: 100vh;
  margin: 0 auto;
`;
