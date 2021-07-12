import styled from 'styled-components';

import PALETTE from '../../../constants/palette';
import Button from '../../@atom/Button/Button';
import Container from '../../@atom/Container/Container';
import Input from '../../@atom/Input/Input';
import Title from '../../@atom/Title/Title';

export const StyledMessageForm = styled.form`
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 100%;
`;

export const DonationMessageTitle = styled(Title)`
  margin-bottom: 4rem;
  text-align: left;
`;

export const NickNameInput = styled(Input)`
  margin-bottom: 3.125rem;
`;

export const TextareaControllerContainer = styled(Container)`
  flex-direction: row;
  justify-content: space-between;
  font-size: 0.875rem;
  line-height: 1.25rem;
  margin-top: 0.5rem;
  margin-bottom: 5.625rem;
  padding: 0 0.5rem;
  color: ${PALETTE.GRAY_400};
`;

export const CheckboxLabel = styled.label`
  display: flex;

  input[type='checkbox'] {
    margin-right: 0.5rem;
  }
`;

export const SubmitButton = styled(Button)`
  margin: 0 auto;
`;
