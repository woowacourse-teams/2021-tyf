import styled from 'styled-components';
import { DEVICE } from '../../../constants/device';
import PALETTE from '../../../constants/palette';
import Input from '../../@atom/Input/Input.styles';

import SubTitle from '../../@atom/SubTitle/SubTitle.styles';
import Title from '../../@atom/Title/Title.styles';
import OutlineButton from '../../@molecule/OutlineButton/OutlineButton.styles';

export const StyledSettlementAccountForm = styled.form`
  @media ${DEVICE.DESKTOP} {
    padding: 3rem;
    width: 32rem;
  }
`;

export const SettlementAccountTitle = styled(Title)`
  margin-bottom: 6.5rem;
  font-size: 1.75rem;

  span {
    display: block;
  }

  @media ${DEVICE.DESKTOP} {
    font-size: 2rem;

    span {
      display: inline;
    }
  }
`;

export const StyledSubTitle = styled(SubTitle)`
  text-align: left;
  margin-bottom: 2.25rem;

  @media ${DEVICE.DESKTOP} {
    font-size: 1.5rem;
  }
`;

export const InputContainer = styled.div`
  width: 100%;
  margin-bottom: 5rem;
`;

export const UploadLabel = styled.label`
  height: auto;
`;

export const FileName = styled.span`
  display: inline-block;
  width: 100%;
  text-align: center;
  margin-bottom: 0.75rem;
`;

export const UploadLabelButton = styled(OutlineButton)`
  pointer-events: none;
`;

export const RegistrationNumberContainer = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

export const RegistrationNumberInput = styled(Input)`
  width: 45%;
`;

export const RegistrationNumberSeparator = styled.span`
  font-size: 2rem;
  margin-bottom: 1rem;
  color: ${PALETTE.GRAY_400};
`;
