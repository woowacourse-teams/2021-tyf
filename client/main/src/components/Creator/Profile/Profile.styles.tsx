import styled from 'styled-components';

export const ProfileImg = styled.img`
  width: 8rem;
  height: 8rem;
  border: 1px solid ${({ theme }) => theme.color.border};
  border-radius: 50%;
  margin-bottom: 1rem;
  object-fit: cover;
  background-color: white;
`;

export const NickName = styled.p`
  font-size: 1.25rem;
  font-weight: 700;
  text-align: center;
`;
