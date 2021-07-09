import styled from 'styled-components';
import Button from '../../@atom/Button/Button';

export const StyledBarButtonWithIcon = styled(Button)`
  display: flex;
  align-items: center;
`;

export const ButtonIcon = styled.img`
  height: 2.25rem;
  object-fit: cover;
`;

export const ButtonContent = styled.span`
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
`;
