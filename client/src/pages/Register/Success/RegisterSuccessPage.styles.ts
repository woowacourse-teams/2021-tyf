import styled from 'styled-components';
import Container from '../../../components/@atom/Container/Container';
import Template from '../../../components/@atom/Template/Template';
import Title from '../../../components/@atom/Title/Title';
import OutlineButton from '../../../components/@molecule/OutlineButton/OutlineButton';
import { SIZE } from '../../../constants/device';
import PALETTE from '../../../constants/palette';

export const StyledTemplate = styled(Template)`
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  max-width: ${SIZE.MOBILE_MAX};
`;

export const RegisterSuccessTitle = styled(Title)`
  flex-grow: 4;
  display: flex;
  justify-content: center;
  align-items: center;
`;

export const SuccessButtonContainer = styled(Container)`
  flex-grow: 1;
`;

export const MyPageOutlineButton = styled(OutlineButton)`
  font-weight: 400;
  color: ${PALETTE.GRAY_500};
  margin-bottom: 2rem;
`;
