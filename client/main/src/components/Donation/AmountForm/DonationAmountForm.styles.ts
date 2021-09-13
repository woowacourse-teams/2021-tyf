import styled from 'styled-components';

import Container from '../../@atom/Container/Container.styles';
import Input from '../../@atom/Input/Input.styles';
import OutlineButton from '../../@molecule/OutlineButton/OutlineButton.styles';

export const StyledDonationAmountForm = styled.form`
  width: 100%;
`;

export const InputLabel = styled.label`
  margin-bottom: 2rem;
`;

export const MoneyInputContainer = styled.div`
  position: relative;
  margin-bottom: 0.5rem;
  min-height: 10rem;

  &::after {
    content: 'tp';
    display: block;
    position: absolute;
    right: 0.5rem;
    bottom: 3.5rem;
  }
`;

export const MoneyInput = styled(Input)`
  font-size: 1.25rem;
  padding: 0 2rem;
  margin-top: 4.5rem;
  text-align: right;

  -moz-appearance: textfield;

  &::-webkit-outer-spin-button,
  &::-webkit-inner-spin-button {
    -webkit-appearance: none;
    margin: 0;
  }
`;

export const ButtonContainer = styled(Container)`
  flex-direction: row;
  padding: 0 0.75rem;
  margin-bottom: 6rem;
`;

export const MoneyAddButton = styled(OutlineButton).attrs({ type: 'button' })`
  width: 100%;
  min-width: 6.5rem;
  height: 2.25rem;
  margin: 0 0.5rem;
  font-size: 0.875rem;
`;
