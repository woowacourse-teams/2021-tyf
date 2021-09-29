import styled from 'styled-components';
import { DEVICE, SIZE } from '../../../constants/device';
import PALETTE from '../../../constants/palette';
import Button from '../../@atom/Button/Button.styles';
import Container from '../../@atom/Container/Container.styles';
import Modal from '../../@atom/Modal/Modal';
import SubTitle from '../../@atom/SubTitle/SubTitle.styles';

export const StyledModal = styled(Modal)`
  position: fixed;
  bottom: 0;
  width: 100vw;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 1.5rem;
  border-radius: 0.625rem 0.625rem 0 0;

  @media ${DEVICE.DESKTOP} {
    position: static;
    width: auto;
    border-radius: 1rem;
    justify-content: center;

    min-height: 40rem;
    padding: 3.5rem;
  }
`;

export const ButtonContainer = styled.div`
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  grid-template-rows: repeat(2, 1fr);
  row-gap: 1.5rem;
  column-gap: 1.5rem;

  margin-top: 5rem;
  margin-bottom: 1.25rem;

  @media ${DEVICE.DESKTOP} {
    grid-template-columns: repeat(3, 1fr);
  }
`;

export const CheckboxContainerList = styled.div`
  margin: 4rem 0 3rem;
`;

export const CheckboxContainer = styled.div`
  display: flex;
  align-items: center;
  margin-bottom: 0.5rem;

  &:nth-of-type(1) {
    padding-bottom: 0.875rem;
    margin-bottom: 0.875rem;
    border-bottom: 1px solid ${PALETTE.GRAY_200};
  }

  & > span {
    margin-left: 1rem;
  }
`;

export const PaymentReadyButton = styled(Button)`
  width: 80%;
`;

export const DonatorTermLink = styled.span`
  font-weight: 400;
  cursor: pointer;
  color: ${({ theme }) => theme.primary.base};
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

export const Caution = styled.p`
  font-size: 0.75rem;
  color: ${PALETTE.GRAY_400};
  line-height: 1rem;
`;

export const SelectAllText = styled.span`
  font-size: 1.125rem;
  font-weight: 500;
`;
