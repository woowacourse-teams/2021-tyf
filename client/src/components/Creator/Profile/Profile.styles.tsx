import styled from 'styled-components';

import Container from '../../@atom/Container/Container';

export const ProfileContainer = styled(Container)``;

export const ProfileImg = styled.img`
  width: 8rem;
  height: 8rem;
  border: 1px solid ${({ theme }) => theme.color.border};
  border-radius: 50%;
  margin-bottom: 1rem;
  object-fit: cover;
`;

export const NickName = styled.p`
  font-size: 1.25rem;
  font-weight: 700;
`;
