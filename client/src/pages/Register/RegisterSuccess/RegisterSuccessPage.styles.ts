import styled from 'styled-components';
import Container from '../../../components/@atom/Container/Container';
import Title from '../../../components/@atom/Title/Title';
import OutlineButton from '../../../components/@molecule/OutlineButton/OutlineButton';
import PALETTE from '../../../constants/palette';

export const RegisterSuccessContainer = styled(Container)`
  min-height: 100vh;
`;

export const RegisterSuccessTitle = styled(Title)`
  margin-bottom: 9.25rem;
`;

export const MyPageOutlineButton = styled(OutlineButton)`
  font-weight: 400;
  color: ${PALETTE.GRAY_500};
  margin-bottom: 4rem;
`;
