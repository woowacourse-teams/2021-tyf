import styled from 'styled-components';

import Button from '../../@atom/Button/Button';
import PALETTE from '../../../constants/palette';

const StyledOutlineButton = styled(Button)`
  background-color: transparent;
  color: ${({ theme }) => theme.color.main};
  border: ${({ theme }) => `1px solid ${theme.color.border}`};

  &:hover {
    background-color: ${PALETTE.GRAY_100};
  }

  &:active {
    background-color: ${PALETTE.GRAY_200};
    border: 1px solid ${PALETTE.GRAY_200};
  }
`;

export default StyledOutlineButton;
