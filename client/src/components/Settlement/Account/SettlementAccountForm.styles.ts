import styled from 'styled-components';
import { DEVICE } from '../../../constants/device';

import Modal from '../../@atom/Modal/Modal';
import SubTitle from '../../@atom/SubTitle/SubTitle.styles';
import Title from '../../@atom/Title/Title.styles';
import StyledOutlineButton from '../../@molecule/OutlineButton/OutlineButton.styles';

export const StyledModal = styled(Modal)`
  //TODO: 실제 적용해보고 수정 필요. 전체화면이면 닫기 버튼도 있어야될듯?
  align-self: flex-end;
  width: 100vw;
  max-height: 100vh;
  display: flex;
  flex-direction: column;
  padding: 0;
  overflow-y: auto;

  @media ${DEVICE.DESKTOP_LARGE} {
    width: 37rem;
    max-height: 39rem;
    border-radius: 0.625rem;
    overflow-y: auto;
    align-self: center;
  }
`;

export const StyledSettlementAccountForm = styled.form`
  padding: 3rem;
`;

export const SettlementAccountTitle = styled(Title)`
  margin-bottom: 6.5rem;
`;

export const StyledSubTitle = styled(SubTitle)`
  text-align: left;
  margin-bottom: 2.25rem;
  font-size: 1.5rem;
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

export const UploadLabelButton = styled(StyledOutlineButton)`
  pointer-events: none;
`;
