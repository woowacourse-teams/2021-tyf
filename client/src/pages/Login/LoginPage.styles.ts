import styled from 'styled-components';
import PALETTE from '../../constants/palette';

import Checkbox from '../../components/@atom/Checkbox/Checkbox';
import Container from '../../components/@atom/Container/Container';
import Title from '../../components/@atom/Title/Title';
import ButtonWithIcon from '../../components/@molecule/BarButtonWithIcon/BarButtonWithIcon';
import OutlineButtonWithIcon from '../../components/@molecule/OutlineBarButtonWithIcon/OutlineBarButtonWithIcon';

export const LoginContainer = styled(Container)`
  min-height: 100vh;
`;

export const LoginButtonContainer = styled(Container)`
  margin-bottom: 4rem;
`;

export const LoginOptionContainer = styled(Container)`
  flex-direction: row;
  justify-content: flex-end;
  // TODO: 아래와 같은 subText의 색상을 theme으로 설정해두면 이후 다크테마 구현에 적절할듯합니다
  color: ${PALETTE.GRAY_500};
`;

export const LoginAnchorContainer = styled(Container)``;

export const LoginTitle = styled(Title)`
  margin-bottom: 4rem;
`;

export const KeepLoginLabel = styled.label`
  margin: 0;
  cursor: pointer;
`;

export const KeepLoginCheckbox = styled(Checkbox)`
  left: -1.5rem;
  top: -0.75rem;
  cursor: pointer;
`;

export const GoogleButton = styled(OutlineButtonWithIcon)`
  color: ${PALETTE.GRAY_500};
  margin-bottom: 1rem;
`;

export const NaverButton = styled(ButtonWithIcon)`
  background-color: #03c75a;
  color: ${PALETTE.WHITE_400};
  margin-bottom: 1rem;

  &:hover,
  &:active {
    background-color: ${PALETTE.GREEN_900};
  }
`;

export const KakaoButton = styled(ButtonWithIcon)`
  background-color: ${PALETTE.YELLOW_400};
  color: ${PALETTE.BLACK_400};
  margin-bottom: 1rem;

  &:hover,
  &:active {
    background-color: ${PALETTE.YELLOW_400};
  }
`;
