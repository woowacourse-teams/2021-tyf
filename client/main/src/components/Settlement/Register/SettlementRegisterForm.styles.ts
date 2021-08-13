import styled from 'styled-components';

import SubTitle from '../../@atom/SubTitle/SubTitle.styles';
import Title from '../../@atom/Title/Title.styles';
import StyledOutlineButton from '../../@molecule/OutlineButton/OutlineButton.styles';

export const StyledSettlementAccountForm = styled.form`
  padding: 3rem;
  width: 37rem;
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
