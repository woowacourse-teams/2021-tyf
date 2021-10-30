import styled from 'styled-components';
import PALETTE from '../../../constants/palette';
import Arrow from '../../../assets/icons/right-arrow.svg';
import Modal from '../Modal/Modal';
import Transition from '../Transition/Transition.styles';
import { DEVICE } from '../../../constants/device';

export const StyledSelectBox = styled.div`
  position: relative;
  width: 100%;
  height: 3rem;
  min-width: 5rem;
  border: 1px solid ${PALETTE.GRAY_300};
  border-radius: 5px;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0 1.5rem;
  white-space: nowrap;
  cursor: pointer;

  &::after {
    content: '';
    background-image: url(${Arrow});
    position: absolute;
    right: 0.5rem;
    margin-top: -5px;
    top: 50%;
    background-size: 0.75rem 0.75rem;
    width: 0.75rem;
    height: 0.75rem;
    transform: rotate(90deg);
  }
`;

export const SelectBoxHeader = styled.div`
  width: 100%;
  height: 100%;
`;

export const OptionModal = styled(Modal)``;

export const ModalTransition = styled(Transition)`
  width: 100vw;
  position: absolute;
  bottom: 0;
  left: 0;
`;

export const DropDownList = styled.ul`
  position: fixed;
  width: 100vw;
  left: 0;
  bottom: 0;
  border-radius: 0.625rem 0.625rem 0 0;
  height: 12rem;
  overflow-y: auto;
  box-shadow: 2px 2px 1px rgba(0, 0, 0, 0.1);
  backface-visibility: hidden;

  @media ${DEVICE.DESKTOP_LARGE} {
    position: absolute;
    top: 100%;
    width: 100%;
    box-shadow: 0;
    height: 9rem;
  }
`;

export const ListItem = styled.li`
  height: 3rem;
  background-color: ${({ theme }) => theme.color.sub};
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;

  &:hover {
    font-weight: 700;
  }

  &:first-child {
    border-radius: 0.625rem 0.625rem 0 0;
  }

  @media ${DEVICE.DESKTOP_LARGE} {
    border-left: 1px solid ${PALETTE.GRAY_300};
    border-right: 1px solid ${PALETTE.GRAY_300};
    &:first-child {
      border-radius: 0;
    }
    &:last-child {
      border-radius: 0 0 0.625rem 0.625rem;
      border-bottom: 1px solid ${PALETTE.GRAY_300};
    }
  }
`;
