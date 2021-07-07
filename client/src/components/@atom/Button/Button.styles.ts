import styled from 'styled-components';

import PALETTE from '../../../constants/palette';

const StyledButton = styled.button`
  height: 3rem;
  min-width: 5rem;
  width: 100%;
  border: none;
  border-radius: 5px;
  font-size: 1rem;
  font-weight: bold;
  color: ${(props) => props.theme.color.sub};
  background-color: ${(props) => props.theme.primary.base};
  cursor: pointer;
  appearance: none;
  transition: 0.2s background-color;
  /* font-family: monospace; */

  &:hover {
    background-color: ${(props) => props.theme.primary.dimmed};
  }

  &:active {
    background-color: ${(props) => props.theme.primary.blackened};
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

export default StyledButton;
