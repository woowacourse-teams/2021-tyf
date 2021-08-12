import styled from 'styled-components';
import PALETTE from '../../../constants/palette';

export const Input = styled.input`
  height: 3rem;
  min-width: 5rem;
  width: 100%;
  border: none;
  border-bottom: 1px solid ${PALETTE.GRAY_300};
  padding: 0 1rem;
  font-size: 1rem;
  transition: all 0.2s ease-in;

  &::placeholder {
    color: ${PALETTE.GRAY_400};
  }

  &:focus {
    border-bottom: 1px solid ${PALETTE.CORAL_300};
  }
`;
