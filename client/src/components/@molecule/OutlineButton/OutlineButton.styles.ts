import { ThemeType } from './../../../theme';
import styled from 'styled-components';

import Button from '../../@atom/Button/Button';
import PALETTE from '../../../constants/palette';

interface Props {
  theme: ThemeType;
}

const StyledOutlineButton = styled(Button)<Props>`
  background-color: transparent;
  color: ${(props) => props.theme.color.main};
  border: 1px solid ${PALETTE.GRAY_300};

  &:hover {
    background-color: ${PALETTE.GRAY_100};
  }
  &:active {
    background-color: ${PALETTE.GRAY_200};
    border: 1px solid ${PALETTE.GRAY_200};
  }
`;

export default StyledOutlineButton;
