import styled from 'styled-components';
import SubTitle from '../../components/@atom/SubTitle/SubTitle';
import Template from '../../components/@atom/Template/Template';
import Title from '../../components/@atom/Title/Title';
import { SIZE } from '../../constants/device';

export const StyledTemplate = styled(Template)`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  max-width: ${SIZE.MOBILE_MAX};
`;

export const InfoTitle = styled(SubTitle)`
  width: 100%;
  text-align: left;
  margin-bottom: 3rem;
`;

export const MoneyInfo = styled(Title)`
  position: relative;
  margin-bottom: 4rem;
  font-size: 3rem;
  width: 100%;

  span:last-of-type {
    font-size: 1rem;
  }
`;
