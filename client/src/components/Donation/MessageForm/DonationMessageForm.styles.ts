import styled from 'styled-components';
import PALETTE from '../../../constants/palette';
import Button from '../../@atom/Button/Button';
import Container from '../../@atom/Container/Container';
import Input from '../../@atom/Input/Input';
import SubTitle from '../../@atom/SubTitle/SubTitle';

export const StyledMessageForm = styled.form`
  display: flex;
  flex-direction: column;
  justify-content: center;
`;

export const StyledSubTitle = styled(SubTitle)`
  font-size: 1.375rem;
  margin-bottom: 4.25rem;
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
  height: 3.125rem;
  width: 90%;
  margin: 0 auto;
`;
