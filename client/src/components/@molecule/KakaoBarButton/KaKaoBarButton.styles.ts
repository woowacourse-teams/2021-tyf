import styled from 'styled-components';

import KakaoLogo from '../../../assets/icons/kakao.svg';
import BarButtonWithIcon from '../../../components/@molecule/BarButtonWithIcon/BarButtonWithIcon';
import PALETTE from '../../../constants/palette';

const KakaoBarButton = styled(BarButtonWithIcon).attrs({
  src: KakaoLogo,
  alt: 'kakao_logo',
})`
  background-color: ${PALETTE.YELLOW_400};
  color: ${PALETTE.BLACK_400};
  margin-bottom: 1rem;

  &:hover,
  &:active {
    background-color: ${PALETTE.YELLOW_400};
  }
`;

export default KakaoBarButton;
