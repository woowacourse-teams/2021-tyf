import styled from 'styled-components';

import Container from '../../@atom/Container/Container';

export const StyledProfileContainer = styled(Container)``;

export const ProfileImg = styled.div`
  width: 8rem;
  height: 8rem;
  border: 1px solid ${({ theme }) => theme.color.border};
  border-radius: 50% 50%;
  margin-bottom: 1rem;
  background-size: 100%;
  background-repeat: no-repeat;
`;

export const NickName = styled.p`
  font-size: 1.25rem;
  font-weight: 700;
`;
