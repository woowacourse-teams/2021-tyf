import styled from 'styled-components';

import PALETTE from '../../../../constants/palette';
import Button from '../../../@atom/Button/Button.styles';
import Container from '../../../@atom/Container/Container.styles';

export const StyledCreatorInfo = styled.section`
  width: 100%;
  margin-top: 4rem;
  margin-bottom: 10rem;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

export const DescriptionContainer = styled(Container)`
  margin: 3rem 0 5rem 0;
  border-top: 1px solid ${({ theme }) => theme.color.border};
  border-bottom: 1px solid ${({ theme }) => theme.color.border};
  padding: 1rem;
  color: ${PALETTE.GRAY_500};
  min-height: 6rem;
  line-height: 1.25;
`;

export const StyledButton = styled(Button)``;
