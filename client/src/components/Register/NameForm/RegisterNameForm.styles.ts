import styled from 'styled-components';
import { DEVICE, SIZE } from '../../../constants/device';
import Button from '../../@atom/Button/Button';
import Container from '../../@atom/Container/Container';
import Title from '../../@atom/Title/Title';

export const StyledRegisterNameForm = styled.form`
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  height: calc(100vh - 4rem);
  margin: 0 auto;
`;

export const RegisterNameTitle = styled(Title)`
  margin-bottom: 4rem;
  text-align: left;
  max-width: ${SIZE.MOBILE_MAX}px;
  margin: 0 auto;

  span {
    display: block;
  }

  @media ${DEVICE.DESKTOP_LARGE} {
    text-align: center;
    max-width: 100%;

    span {
      display: inline;
    }
  }
`;

export const NameInputContainer = styled(Container)`
  margin-bottom: 4rem;
  max-width: ${SIZE.MOBILE_MAX}px;
  margin: 0 auto;
`;

export const StyledButton = styled(Button)`
  max-width: ${SIZE.MOBILE_MAX}px;
  margin: 0 auto;
`;
