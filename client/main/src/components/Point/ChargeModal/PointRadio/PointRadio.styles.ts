import styled from 'styled-components';
import PALETTE from '../../../../constants/palette';

export const StyledPointSelect = styled.div`
  input[type='radio'] {
    display: none;
  }

  label {
    cursor: pointer;
    display: inline-block;
    text-align: center;
    font-weight: 600;
    width: 7rem;
    height: 2.25rem;
    line-height: 2.25rem;
    border: 1px solid ${PALETTE.GRAY_300};
    border-radius: 0.5rem;

    &:hover {
      background: ${PALETTE.GRAY_100};
    }
  }

  input[type='radio']:checked + label {
    background: ${PALETTE.GRAY_300};
  }
`;
