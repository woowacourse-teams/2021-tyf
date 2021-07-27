import styled from 'styled-components';
import PALETTE from '../../../constants/palette';
import Container from '../../@atom/Container/Container';

import Modal from '../../@atom/Modal/Modal';

export const StyledModal = styled(Modal)`
  width: 29rem;
  padding: 1.25rem;
  border-radius: 1rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

export const StyledContainerButton = styled(Container)`
  position: relative;
  width: 12.75rem;
  height: 13.25rem;
  border: 1px solid ${PALETTE.GRAY_300};
  border-radius: 5px;
  cursor: pointer;
  transition: 0.2s all;
  font-weight: bold;

  :hover {
    border: 1px solid ${PALETTE.GRAY_400};
  }
`;

export const Name = styled.span`
  position: absolute;
  bottom: 0.75rem;
`;
