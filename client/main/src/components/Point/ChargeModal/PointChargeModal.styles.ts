import styled from 'styled-components';
import { SIZE } from '../../../constants/device';
import PALETTE from '../../../constants/palette';
import Button from '../../@atom/Button/Button.styles';
import Container from '../../@atom/Container/Container.styles';
import Modal from '../../@atom/Modal/Modal';
import SubTitle from '../../@atom/SubTitle/SubTitle.styles';

export const StyledModal = styled(Modal)`
  border-radius: 1rem;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  min-height: 40rem;
  padding: 3.5rem;
`;

export const ButtonContainer = styled.div`
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  grid-template-rows: repeat(2, 1fr);
  row-gap: 1.5rem;
  column-gap: 1.5rem;

  margin-top: 5rem;
  margin-bottom: 1.25rem;
`;

export const StyledPointSelect = styled.div`
  input[type='radio'] {
    display: none;
  }

  label {
    cursor: pointer;
    display: inline-block;
    text-align: center;
    font-weight: 600;
    width: 7rem;
    height: 2.25rem;
    line-height: 2.25rem;
    border: 1px solid ${PALETTE.GRAY_300};
    border-radius: 0.5rem;

    &:hover {
      background: ${PALETTE.GRAY_100};
    }
  }

  input[type='radio']:checked + label {
    background: ${PALETTE.GRAY_300};
  }
`;

export const CheckboxContainer = styled.div`
  margin: 4rem 0 3rem;

  div {
    margin-bottom: 0.5rem;
  }

  div > span {
    margin-left: 1rem;
  }
`;

export const PaymentReadyButton = styled(Button)`
  width: 80%;
`;

export const DonatorTermLink = styled.span`
  text-decoration: underline;
  font-weight: 500;
  cursor: pointer;
`;

export const StyledSubTitle = styled(SubTitle)`
  margin-bottom: 4rem;
`;

export const PaymentButtonContainer = styled(Container)`
  max-width: ${SIZE.MOBILE_MAX}px;

  button {
    margin-bottom: 1rem;
  }
`;
