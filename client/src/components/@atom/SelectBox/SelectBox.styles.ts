import styled from 'styled-components';
import PALETTE from '../../../constants/palette';
import Arrow from '../../../assets/icons/right-arrow.svg';

export const StyledSelectBox = styled.div`
  position: relative;
  width: 100%;
  height: 3rem;
  min-width: 5rem;
  border: 1px solid ${PALETTE.GRAY_300};
  border-radius: 5px;

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

export const Select = styled.select`
  text-align-last: center;
  width: 100%;
  height: 100%;
  border: 0;
  font-size: 1rem;
  color: ${({ theme }) => theme.color.main};
  appearance: none;
  border-radius: 5px;
`;
