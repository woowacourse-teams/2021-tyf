import styled from 'styled-components';
import { SIZE } from '../../constants/device';
import PALETTE from '../../constants/palette';
import Button from '../@atom/Button/Button.styles';
import Container from '../@atom/Container/Container.styles';

export const StyledErrorFallback = styled(Container)`
  width: 100vw;
  height: 100vh;
  position: fixed;
  background-color: ${PALETTE.WHITE_400};
`;

export const StyledContainer = styled(Container)`
  height: 30rem;
  max-width: ${SIZE.DESKTOP_LARGE}px;
  justify-content: space-around;
  align-items: center;
`;

export const StyledButton = styled(Button)`
  max-width: ${SIZE.MOBILE_MIN}px;
`;
