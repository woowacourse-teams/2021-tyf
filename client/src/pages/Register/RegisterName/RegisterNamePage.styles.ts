import styled from 'styled-components';
import Container from '../../../components/@atom/Container/Container';
import Template from '../../../components/@atom/Template/Template';
import Title from '../../../components/@atom/Title/Title';

export const StyledTemplate = styled(Template)`
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
`;

export const RegisterNameContainer = styled(Container)`
  min-height: 100vh;
`;

export const RegisterNameTitle = styled(Title)`
  margin-bottom: 4rem;
  text-align: left;
`;

export const NameInputContainer = styled(Container)`
  margin-bottom: 4rem;
`;
