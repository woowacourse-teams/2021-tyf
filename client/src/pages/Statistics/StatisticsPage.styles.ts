import styled from 'styled-components';
import Container from '../../components/@atom/Container/Container';
import SubTitle from '../../components/@atom/SubTitle/SubTitle';
import Title from '../../components/@atom/Title/Title';

export const StatisticsContainer = styled(Container)`
  height: 100vh;
`;

export const InfoTitle = styled(SubTitle)`
  margin-bottom: 6rem;
`;

export const MoneyInfo = styled(Title)`
  margin-bottom: 4rem;
  font-size: 3rem;

  span {
    font-size: 1rem;
  }
`;
