import styled from 'styled-components';
import PALETTE from '../../constants/palette';

import Checkbox from '../../components/@atom/Checkbox/Checkbox.styles';
import Container from '../../components/@atom/Container/Container.styles';
import Title from '../../components/@atom/Title/Title.styles';

export const LoginButtonContainer = styled(Container)`
  margin-bottom: 4rem;
`;

export const LoginOptionContainer = styled(Container)`
  flex-direction: row;
  justify-content: flex-end;
  color: ${PALETTE.GRAY_500};
`;

export const LoginAnchorContainer = styled(Container)``;

export const LoginTitle = styled(Title)`
  margin-bottom: 4rem;
`;

export const KeepLoginLabel = styled.label`
  margin: 0;
  cursor: pointer;
  display: flex;
  align-items: center;
  margin-right: 0.625rem;
  font-size: 0.875rem;
`;

export const KeepLoginCheckbox = styled(Checkbox)`
  margin-right: 0.5rem;
`;
