import styled from 'styled-components';

import { SIZE } from '../../../constants/device';
import { MIN_PAGE_HEIGHT } from '../../../constants/style';
import Checkbox from '../../@atom/Checkbox/Checkbox';
import Container from '../../@atom/Container/Container.styles';

export const StyledDonatorForm = styled.form`
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  width: 100%;
  height: ${MIN_PAGE_HEIGHT};
  max-width: ${SIZE.MOBILE_MAX}px;
`;

export const InputContainer = styled(Container)``;

export const TermLabel = styled.label`
  padding: 0.5rem;
  margin: 1.5rem 0;
  cursor: pointer;
  display: flex;
  align-self: flex-start;
  align-items: center;
`;

export const TermCheckbox = styled(Checkbox)`
  margin-right: 1rem;
`;

export const TermLink = styled.span`
  color: ${({ theme }) => theme.primary.base};

  &:hover {
    color: ${({ theme }) => theme.primary.dimmed};
  }

  &:active {
    color: ${({ theme }) => theme.primary.blackened};
  }
`;
