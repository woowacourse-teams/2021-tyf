import styled from 'styled-components';
import Container from '../../@atom/Container/Container';

export const StyledProfileContainer = styled(Container)``;

export const ProfileImg = styled.div`
  width: 10.75rem;
  height: 10.75rem;
  border: 1px solid ${({ theme }) => theme.color.border};
  border-radius: 50% 50%;
  margin-bottom: 1.75rem;
  background-size: 100%;
  background-repeat: no-repeat;
`;
