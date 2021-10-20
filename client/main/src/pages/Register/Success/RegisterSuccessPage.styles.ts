import styled from 'styled-components';
import Container from '../../../components/@atom/Container/Container.styles';
import SubTitle from '../../../components/@atom/SubTitle/SubTitle.styles';
import Template from '../../../components/@atom/Template/Template';
import Title from '../../../components/@atom/Title/Title.styles';
import OutlineButton from '../../../components/@molecule/OutlineButton/OutlineButton.styles';
import { SIZE } from '../../../constants/device';
import PALETTE from '../../../constants/palette';

export const StyledTemplate = styled(Template)`
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  max-width: ${SIZE.MOBILE_MAX}px;
`;

export const RegisterSuccessTitle = styled(Title)`
  flex-grow: 4;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 4rem;
`;

export const SuccessButtonContainer = styled(Container)`
  flex-grow: 1;
`;

export const MyPageOutlineButton = styled(OutlineButton)`
  font-weight: 400;
  color: ${PALETTE.GRAY_500};
  margin-bottom: 2rem;
`;

export const SettlementOutlineButton = styled(OutlineButton)`
  font-weight: 400;
  color: ${PALETTE.GRAY_500};
  margin-bottom: 4rem;
`;

export const StyledSubTitle = styled(SubTitle)`
  font-size: 1.5rem;
  margin-top: 2rem;
  margin-bottom: 2rem;
`;
