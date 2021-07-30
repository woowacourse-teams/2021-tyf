import styled from 'styled-components';

import { SIZE } from '../../../constants/device';
import Checkbox from '../../@atom/Checkbox/Checkbox';
import Container from '../../@atom/Container/Container';

export const StyledDonatorForm = styled.form`
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  width: 100%;
  height: calc(100vh - 4rem);
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

export const TermLink = styled.a`
  color: ${({ theme }) => theme.primary.base};

  &:hover {
    color: ${({ theme }) => theme.primary.dimmed};
  }

  &:active {
    color: ${({ theme }) => theme.primary.blackened};
  }
`;
