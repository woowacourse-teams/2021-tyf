import styled from 'styled-components';
import Checkbox from '../../@atom/Checkbox/Checkbox';
import Container from '../../@atom/Container/Container';
import Title from '../../@atom/Title/Title';

export const StyledRegisterTermsForm = styled.form`
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
`;

export const TermsContainer = styled(Container)`
  flex-direction: column;
  justify-content: space-around;
  align-items: flex-start;
`;

export const RegisterTermsTitle = styled(Title)`
  margin-bottom: 4rem;
  text-align: left;
`;

export const TermLabel = styled.label`
  padding: 0.5rem;
  cursor: pointer;
  display: flex;
  align-items: center;
`;

export const TermCheckbox = styled(Checkbox)`
  margin-right: 1rem;
`;

export const Divider = styled.hr`
  width: 100%;
  border: 1px solid ${({ theme }) => theme.color.border};
  margin: 1rem 0;
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
