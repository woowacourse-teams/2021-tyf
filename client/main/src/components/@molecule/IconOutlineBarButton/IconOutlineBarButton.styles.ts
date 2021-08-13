import styled from 'styled-components';

import PALETTE from '../../../constants/palette';
import Button from '../../@atom/Button/Button.styles';

export const StyledIconOutlineBarButton = styled(Button)`
  display: flex;
  align-items: center;
  background-color: transparent;
  color: ${({ theme }) => theme.color.main};
  border: ${({ theme }) => `1px solid ${theme.color.border}`};

  &:hover {
    background-color: ${PALETTE.GRAY_100};
  }

  &:active {
    background-color: ${PALETTE.GRAY_200};
    border: 1px solid ${PALETTE.GRAY_200};
  }
`;

export const ButtonIcon = styled.img`
  padding: 0.5rem;
  height: 2.25rem;
`;

export const ButtonContent = styled.span`
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
`;
