import styled from 'styled-components';
import PALETTE from '../../../constants/palette';

import Container from '../../../components/@atom/Container/Container';
import Title from '../../../components/@atom/Title/Title';
import InputWithMessage from '../../../components/@molecule/InputWithMessage/InputWithMessage';

export const RegisterAddressContainer = styled(Container)`
  min-height: 100vh;
`;

export const RegisterAddressTitle = styled(Title)`
  margin-bottom: 8.5rem;
`;

export const AddressInputWithMessage = styled(InputWithMessage)``;

export const AddressInputContainer = styled(Container)`
  position: relative;
  margin-bottom: 4rem;

  &::before {
    content: 'https://thankyoufor.com/';
    position: absolute;
    top: 20%;
    left: 0;
    color: ${PALETTE.GRAY_500};
  }

  input {
    padding-left: 12rem;
  }
`;
