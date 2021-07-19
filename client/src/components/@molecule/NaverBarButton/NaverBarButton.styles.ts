import styled from 'styled-components';

import BarButtonWithIcon from '../BarButtonWithIcon/BarButtonWithIcon';
import PALETTE from '../../../constants/palette';
import NaverLogo from '../../../assets/icons/naver.svg';

const NaverBarButton = styled(BarButtonWithIcon).attrs({
  src: NaverLogo,
  alt: 'naver_logo',
})`
  background-color: #03c75a;
  color: ${PALETTE.WHITE_400};
  margin-bottom: 1rem;

  &:hover,
  &:active {
    background-color: ${PALETTE.GREEN_900};
  }
`;

export default NaverBarButton;
