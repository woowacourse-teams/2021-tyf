import styled from 'styled-components';
import Container from '../../@atom/Container/Container';
import Title from '../../@atom/Title/Title';
import PALETTE from '../../../constants/palette';

export const StyledRegisterAddressForm = styled.form`
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
`;

export const RegisterAddressTitle = styled(Title)`
  margin-bottom: 4rem;
  text-align: left;
`;

export const AddressInputContainer = styled(Container)`
  position: relative;
  margin-bottom: 4rem;

  &::before {
    content: 'https://thankyoufor.com/creator/';
    position: absolute;
    top: 20%;
    left: 0.5rem;
    color: ${PALETTE.GRAY_500};
  }

  input {
    padding-left: 16rem;
  }
`;
