import styled from 'styled-components';
import Container from '../../../components/@atom/Container/Container';
import Template from '../../../components/@atom/Template/Template';
import Title from '../../../components/@atom/Title/Title';
import PALETTE from '../../../constants/palette';

export const StyledTemplate = styled(Template)`
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
`;

export const RegisterAddressContainer = styled(Container)`
  min-height: 100vh;
`;

export const RegisterAddressTitle = styled(Title)`
  margin-bottom: 4rem;
  text-align: left;
`;

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
