import styled from 'styled-components';
import { SIZE } from '../../constants/device';

export const StyledLogin = styled.div`
  min-height: calc(100vh - 10rem);
  display: flex;
  justify-items: center;
  align-items: center;
`;

export const StyledLoginForm = styled.form`
  max-width: ${SIZE.MOBILE_MAX}px;
  width: 100%;
  margin: 0 auto;
`;

export const LoginTitle = styled.h2`
  text-align: center;
  margin-bottom: 5rem;
`;

export const LoginContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;

  input {
    margin-bottom: 2rem;
  }
`;
