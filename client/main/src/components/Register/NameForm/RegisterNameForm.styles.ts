import styled from 'styled-components';
import { DEVICE, SIZE } from '../../../constants/device';
import { MIN_PAGE_HEIGHT } from '../../../constants/style';
import Button from '../../@atom/Button/Button.styles';
import Container from '../../@atom/Container/Container.styles';
import Title from '../../@atom/Title/Title.styles';

export const StyledRegisterNameForm = styled.form`
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  height: ${MIN_PAGE_HEIGHT};
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
