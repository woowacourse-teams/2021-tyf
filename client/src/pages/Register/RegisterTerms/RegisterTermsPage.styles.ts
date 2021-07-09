import styled from 'styled-components';
import Checkbox from '../../../components/@atom/Checkbox/Checkbox';
import Container from '../../../components/@atom/Container/Container';
import Title from '../../../components/@atom/Title/Title';

export const RegisterTermsContainer = styled(Container)`
  min-height: 100vh;
`;

export const TermsContainer = styled(Container)`
  margin-bottom: 4rem;
  align-items: flex-start;
`;

export const RegisterTermsTitle = styled(Title)`
  margin-bottom: 4rem;
`;

export const TermLabel = styled.label`
  padding: 0.5rem 0 0.5rem 1.5rem;
  cursor: pointer;
`;

export const TermCheckbox = styled(Checkbox)`
  left: -1.5rem;
  top: -0.75rem;
  cursor: pointer;
`;

export const Divider = styled.hr`
  width: 100%;
  border: 1px solid ${({ theme }) => theme.color.border};
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
