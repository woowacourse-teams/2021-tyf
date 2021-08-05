import styled from 'styled-components';

import { DEVICE, SIZE } from '../../../constants/device';
import PALETTE from '../../../constants/palette';
import { MIN_PAGE_HEIGHT } from '../../../constants/style';
import Button from '../../@atom/Button/Button.styles';
import Container from '../../@atom/Container/Container.styles';
import Input from '../../@atom/Input/Input.styles';
import SubTitle from '../../@atom/SubTitle/SubTitle.styles';
import TextButton from '../../@atom/TextButton/TextButton.styles';
import Title from '../../@atom/Title/Title.styles';

export const StyledRefundCertificationForm = styled.form`
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  height: ${MIN_PAGE_HEIGHT};
  margin: 0 auto;
  max-width: ${SIZE.MOBILE_MAX}px;
`;

export const RefundCertificationTitle = styled(Title)`
  margin-bottom: 4rem;
  text-align: center;
  max-width: ${SIZE.MOBILE_MAX}px;
  margin: 0 auto;
  font-size: 2rem;
  word-break: break-alls;

  span {
    display: block;
  }

  @media ${DEVICE.DESKTOP_LARGE} {
    text-align: center;
    max-width: 100%;

    span {
      display: inline;
    }
  }
`;

export const CertificationInputContainer = styled(Container)``;

export const StyledSubTitle = styled(SubTitle)`
  width: 100%;
  text-align: left;
`;

export const InputContainer = styled.div`
  position: relative;
  width: 100%;
  margin: 1rem 0;
  @media ${DEVICE.DESKTOP_LARGE} {
    margin-top: 2rem;
  }
`;

export const CertInput = styled(Input)`
  padding-right: 4rem;
`;

export const RestTime = styled.span`
  font-size: 0.875rem;
  color: ${PALETTE.CORAL_400};
  position: absolute;
  bottom: 1rem;
  right: 1rem;
`;

export const ButtonContainer = styled(Container)``;

export const StyledButton = styled(Button)`
  margin-bottom: 2rem;
`;

export const StyledTextButton = styled(TextButton)``;
