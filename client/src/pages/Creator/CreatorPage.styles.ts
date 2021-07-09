import styled from 'styled-components';
import Container from '../../components/@atom/Container/Container';

import PALETTE from '../../constants/palette';

export const CreatorPageContainer = styled(Container)`
  height: 100vh;
`;

export const ProfileContainer = styled(Container)`
  margin-bottom: 8rem;
`;

export const DescriptionContainer = styled(Container)`
  margin: 3rem 0 5rem 0;
  border-top: 1px solid ${({ theme }) => theme.color.border};
  border-bottom: 1px solid ${({ theme }) => theme.color.border};
  padding: 1rem;
  color: ${PALETTE.GRAY_500};
`;
