import styled from 'styled-components';
import Checkbox from '../../../components/@atom/Checkbox/Checkbox';
import Container from '../../../components/@atom/Container/Container';
import Template from '../../../components/@atom/Template/Template';
import Title from '../../../components/@atom/Title/Title';

// TODO: 아래 css 모듈화
export const StyledTemplate = styled(Template)`
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
`;

export const TermsContainer = styled(Container)`
  margin-bottom: 4rem;
  align-items: flex-start;

  /* padding-bottom: 8rem; */
`;

export const RegisterTermsTitle = styled(Title)`
  margin-bottom: 4rem;
  width: 100%;
  text-align: left;
  line-height: 2.5rem;
  padding: 0 1rem;
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
