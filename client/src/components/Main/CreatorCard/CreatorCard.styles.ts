import styled from 'styled-components';

import PALETTE from '../../../constants/palette';

export const StyledCreatorCard = styled.article`
  width: 8.125rem;
  height: 9.5rem;
  background-color: ${PALETTE.GRAY_100};
  border-radius: 5px;
  box-shadow: 2px 2px 1px rgba(0, 0, 0, 0.1);
  overflow: hidden;
`;

export const ProfileImg = styled.img`
  width: 100%;
  height: 80%;
  object-fit: cover;
`;

export const Name = styled.p`
  text-align: center;
  line-height: 1.625rem;
  font-weight: 400;
`;
