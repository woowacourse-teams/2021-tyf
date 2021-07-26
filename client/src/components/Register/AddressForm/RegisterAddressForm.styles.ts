import styled from 'styled-components';
import Container from '../../@atom/Container/Container';
import Title from '../../@atom/Title/Title';
import PALETTE from '../../../constants/palette';
import { DEVICE, SIZE } from '../../../constants/device';
import Button from '../../@atom/Button/Button';

export const StyledRegisterAddressForm = styled.form`
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  height: calc(100vh - 4rem);
`;

export const RegisterAddressTitle = styled(Title)`
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

export const AddressInputContainer = styled(Container)`
  position: relative;
  margin-bottom: 4rem;
  margin: 0 auto;
  max-width: ${SIZE.MOBILE_MAX}px;

  &::before {
    content: 'https://thankyoufor.com/creator/';
    position: absolute;
    top: 20%;
    left: 0.5rem;
    color: ${PALETTE.GRAY_500};
  }

  input {
    padding-left: 16.25rem;
  }
`;

export const StyledButton = styled(Button)`
  margin: 0 auto;
  max-width: ${SIZE.MOBILE_MAX}px;
`;
