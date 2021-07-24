import styled from 'styled-components';

import Button from '../../@atom/Button/Button';

export const StyledIconBarButton = styled(Button)`
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  padding-left: 2.25rem;
`;

export const ButtonIcon = styled.img`
  height: 2.25rem;
  object-fit: cover;
  position: absolute;
  left: 0.125rem;
`;
