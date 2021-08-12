import styled from 'styled-components';

import SubTitle from '../../@atom/SubTitle/SubTitle.styles';
import Camera from '../../../assets/icons/camera.svg';
import { DEVICE } from '../../../constants/device';
import PALETTE from '../../../constants/palette';

export const StyledUserSettingForm = styled.form`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
`;

export const ProfileImgInputLabel = styled.label`
  position: relative;
  width: 8rem;
  height: 8rem;
  border: 1px solid ${({ theme }) => theme.color.border};
  border-radius: 50%;
  overflow: hidden;
  margin-bottom: 1rem;
  cursor: pointer;
  margin: 5rem 0 3rem;

  ::after {
    content: url(${Camera});
    width: 100%;
    height: 100%;
    position: absolute;
    top: 0;
    left: 0;
    display: flex;
    justify-content: center;
    align-items: center;
    transition: all 0.2s;
    background-color: rgba(0, 0, 0, 0.3);
  }

  &:hover::after {
    background-color: rgba(0, 0, 0, 0.4);
  }

  @media ${DEVICE.DESKTOP} {
    width: 12rem;
    height: 12rem;
  }
`;

export const ProfileImg = styled.img`
  width: 100%;
  height: 100%;
  object-fit: cover;
`;

export const StyledSubTitle = styled(SubTitle)`
  margin-bottom: 2rem;
  text-align: left;
`;

export const NickNameInputContainer = styled.div`
  width: 100%;
  margin-bottom: 4.875rem;
`;

export const IntroductionTextareaContainer = styled.div`
  width: 100%;
  margin-bottom: 7.5rem;
`;

export const TextareaLengthLimit = styled.p`
  margin-top: 0.5rem;
  text-align: right;
  font-size: 0.875rem;
  color: ${PALETTE.GRAY_400};
`;
