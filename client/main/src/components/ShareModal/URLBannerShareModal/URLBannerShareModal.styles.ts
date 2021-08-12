import styled from 'styled-components';
import PALETTE from '../../../constants/palette';
import { Z_INDEX } from '../../../constants/style';
import Container from '../../@atom/Container/Container.styles';

import Modal from '../../@atom/Modal/Modal';
import SubTitle from '../../@atom/SubTitle/SubTitle.styles';
import TextButton from '../../@atom/TextButton/TextButton.styles';

export const StyledModal = styled(Modal)`
  width: 37.5rem;
  padding: 2.25rem;
  border-radius: 1rem;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
`;

export const DisplayButtonContainer = styled(Container)`
  width: 100%;
  height: 16.75rem;
  background-color: ${PALETTE.BLACK_400};
  border-radius: 0.5rem;
  margin-bottom: 2.25rem;
  position: relative;
`;

export const DisplayButton = styled.img`
  height: 2.5rem;
`;

export const SourceCodeContainer = styled.div`
  width: 100%;
  margin-bottom: 1.5rem;
`;

export const StyledSubTitle = styled(SubTitle)`
  font-weight: 600;
  font-size: 1.125rem;
  text-align: left;
  margin-bottom: 1rem;
`;

export const CloseButton = styled(TextButton)`
  font-size: 1.125rem;
  font-weight: 500;
  margin-top: 1.25rem; ;
`;

export const PaletteContainer = styled(Container)`
  flex-direction: row;
  position: absolute;
  bottom: 1rem;
`;

interface ColorSelectButtonProps {
  color: string;
}

export const ColorSelectButton = styled.button<ColorSelectButtonProps>`
  width: 2rem;
  height: 2rem;
  border-radius: 50%;
  border: none;
  background-color: ${({ color }) => color};
  margin: 0 0.25rem;
  cursor: pointer;
  position: relative;

  :hover::after {
    content: '';
    width: 100%;
    height: 100%;
    border-radius: 50%;
    position: absolute;
    background-color: rgba(255, 255, 255, 10%);
    top: 0;
    left: 0;
    z-index: ${Z_INDEX.FOREGROUND};
  }
`;
