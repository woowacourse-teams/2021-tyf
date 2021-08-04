import styled from 'styled-components';

import { SIZE } from '../../../constants/device';
import PALETTE from '../../../constants/palette';
import Button from '../../@atom/Button/Button.styles';
import Container from '../../@atom/Container/Container.styles';
import Input from '../../@atom/Input/Input.styles';
import Title from '../../@atom/Title/Title.styles';

export const StyledMessageForm = styled.form`
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 100%;
  max-width: ${SIZE.MOBILE_MAX}px;
  margin: 0 auto;
`;

export const DonationMessageTitle = styled(Title)`
  margin-bottom: 2rem;
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
