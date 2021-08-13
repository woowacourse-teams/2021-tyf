import styled from 'styled-components';

import KakaoLogo from '../../../assets/icons/kakao.svg';
import IconBarButton from '../IconBarButton/IconBarButton';
import PALETTE from '../../../constants/palette';

const KakaoBarButton = styled(IconBarButton).attrs({
  src: KakaoLogo,
  alt: 'kakao logo',
})`
  background-color: ${PALETTE.YELLOW_400};
  color: ${PALETTE.BROWN_400};
  margin-bottom: 1rem;

  &:hover,
  &:active {
    background-color: ${PALETTE.YELLOW_400};
  }
`;

export default KakaoBarButton;
