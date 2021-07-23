import styled from 'styled-components';
import Container from '../../@atom/Container/Container';
import Title from '../../@atom/Title/Title';

export const StyledRegisterNameForm = styled.form`
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
`;

export const RegisterNameTitle = styled(Title)`
  margin-bottom: 4rem;
  text-align: left;
`;

export const NameInputContainer = styled(Container)`
  margin-bottom: 4rem;
`;
