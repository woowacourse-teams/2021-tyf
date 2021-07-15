import styled from 'styled-components';

import Container from '../../../components/@atom/Container/Container';
import Title from '../../../components/@atom/Title/Title';
import PALETTE from '../../../constants/palette';

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

export const TermLink = styled.a`
  color: ${({ theme }) => theme.primary.base};

  &:hover {
    color: ${({ theme }) => theme.primary.dimmed};
  }

  &:active {
    color: ${({ theme }) => theme.primary.blackened};
  }
`;
