import styled from 'styled-components';
import PALETTE from '../../../constants/palette';

export const Button = styled.button`
  height: 3rem;
  min-width: 5rem;
  width: 100%;
  border: none;
  border-radius: 5px;
  font-size: 1rem;
  font-weight: bold;
  color: ${PALETTE.WHITE_400};
  background-color: ${PALETTE.CORAL_400};
  cursor: pointer;
  appearance: none;
  transition: 0.2s background-color;

  &:hover {
    background-color: ${PALETTE.CORAL_700};
  }

  &:active {
    background-color: ${PALETTE.CORAL_900};
    transition: none;
  }

  &:disabled {
    background-color: ${PALETTE.GRAY_300};
    color: ${PALETTE.GRAY_500};
    cursor: inherit;

    &:hover,
    &:active {
      background-color: ${PALETTE.GRAY_300};
    }
  }
`;
