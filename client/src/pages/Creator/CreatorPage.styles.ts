import styled from 'styled-components';
import Container from '../../components/@atom/Container/Container';
import Template from '../../components/@atom/Template/Template';

import PALETTE from '../../constants/palette';

export const StyledTemplate = styled(Template)`
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
`;

export const ProfileContainer = styled(Container)`
  margin-top: 4rem;
  margin-bottom: 10rem;
`;

export const DescriptionContainer = styled(Container)`
  margin: 3rem 0 5rem 0;
  border-top: 1px solid ${({ theme }) => theme.color.border};
  border-bottom: 1px solid ${({ theme }) => theme.color.border};
  padding: 1rem;
  color: ${PALETTE.GRAY_500};
`;
