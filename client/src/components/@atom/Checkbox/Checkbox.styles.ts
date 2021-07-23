import styled from 'styled-components';
import checkIcon from "../../../assets/icons/check.svg"

export const StyledCheckbox = styled.input`
  position: relative;
  appearance: none;
  width: 1rem;
  height: 1rem;
  cursor: inherit;

  &:after {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    border: 1px solid ${({ theme }) => theme.color.border};
    border-radius: 5px;
    display: block;
  }

  &:checked::after {
    content: url(${checkIcon});
    color: transparent;
    text-align: center;
    line-height: 1rem;
    font-size: 1.125rem;
    background-color: ${({ theme }) => theme.primary.base};
    border: 1px solid ${({ theme }) => theme.primary.base};
  }
`;
