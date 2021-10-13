import styled from 'styled-components';
import PALETTE from '../../../../constants/palette';
import { theme } from '../../../../theme';

export const StyledPointSelect = styled.div`
  input[type='radio'] {
    display: none;
  }

  label {
    cursor: pointer;
    display: flex;
    text-align: center;
    font-weight: 700;
    width: 7rem;
    height: 2.25rem;
    align-items: center;
    justify-content: center;
    border: 1px solid ${PALETTE.GRAY_300};
    border-radius: 0.5rem;

    &:hover {
      background: ${PALETTE.GRAY_100};
    }
  }

  input[type='radio']:checked + label {
    border: 2px solid ${PALETTE.CORAL_300};
  }
`;
