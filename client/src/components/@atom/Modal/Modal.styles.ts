import { theme } from './../../../theme';
import { Z_INDEX, MIN_WIDTH } from './../../../constants/style';
import styled from 'styled-components';

export const ModalOuter = styled.div`
  width: 100vw;
  height: 100vh;
  z-index: ${Z_INDEX.FORGROUND};
  position: fixed;
  top: 0;
  left: 0;
  background-color: rgba(0, 0, 0, 0.7);
  display: flex;
  justify-content: center;
  align-items: center;
`;

export const ModalInner = styled.div`
  background-color: ${({ theme }) => theme.color.sub};
  min-width: ${MIN_WIDTH};
  border-radius: 1rem;
`;
