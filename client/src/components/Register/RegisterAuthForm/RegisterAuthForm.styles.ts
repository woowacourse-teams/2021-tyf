import styled from 'styled-components';

import Container from '../../../components/@atom/Container/Container';
import Title from '../../../components/@atom/Title/Title';
import BarButtonWithIcon from '../../../components/@molecule/BarButtonWithIcon/BarButtonWithIcon';
import OutlineBarButtonWithIcon from '../../../components/@molecule/OutlineBarButtonWithIcon/OutlineBarButtonWithIcon';
import PALETTE from '../../../constants/palette';
import GoogleLogo from '../../../assets/icons/google.svg';
import NaverLogo from '../../../assets/icons/naver.svg';
import KakaoLogo from '../../../assets/icons/kakao.svg';

export const RegisterButtonContainer = styled(Container)`
  margin-bottom: 4rem;
`;

export const RegisterOptionContainer = styled(Container)`
  flex-direction: row;
  justify-content: flex-end;
  color: ${PALETTE.GRAY_500};
`;

export const RegisterAnchorContainer = styled(Container)``;

export const RegisterTitle = styled(Title)`
  margin-bottom: 4rem;
`;

export const KeepRegisterLabel = styled.label`
  margin: 0;
  cursor: pointer;
`;

export const GoogleButton = styled(OutlineBarButtonWithIcon).attrs({
  src: GoogleLogo,
  alt: 'google_logo',
})`
  color: ${PALETTE.GRAY_500};
  margin-bottom: 1rem;
`;

export const NaverButton = styled(BarButtonWithIcon).attrs({
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

export const KakaoButton = styled(BarButtonWithIcon).attrs({
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

export const TermLink = styled.a`
  color: ${({ theme }) => theme.primary.base};

  &:hover {
    color: ${({ theme }) => theme.primary.dimmed};
  }

  &:active {
    color: ${({ theme }) => theme.primary.blackened};
  }
`;
