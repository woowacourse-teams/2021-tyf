import styled from 'styled-components';

import PALETTE from '../../../constants/palette';

const StyledInput = styled.input`
  height: 3rem;
  min-width: 5rem;
  width: 100%;
  border: none;
  border-bottom: 1px solid ${PALETTE.GRAY_300};
  padding: 0 1rem;

  &::placeholder {
    color: ${(props) => props.theme.color.placeholder};
  }
`;

export default StyledInput;
