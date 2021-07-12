import styled from 'styled-components';
import SubTitle from '../../components/@atom/SubTitle/SubTitle';
import Template from '../../components/@atom/Template/Template';
import Title from '../../components/@atom/Title/Title';

export const StyledTemplate = styled(Template)`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

export const InfoTitle = styled(SubTitle)`
  position: absolute;
  top: -12rem;
  width: 100%;
  text-align: left;
`;

export const MoneyInfo = styled(Title)`
  position: relative;
  margin-bottom: 4rem;
  font-size: 3rem;
  width: 100%;

  span {
    font-size: 1rem;
  }
`;
