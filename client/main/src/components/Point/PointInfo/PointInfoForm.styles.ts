import styled from 'styled-components';

import Button from '../../@atom/Button/Button.styles';
import SubTitle from '../../@atom/SubTitle/SubTitle.styles';
import Title from '../../@atom/Title/Title.styles';

export const StyledPointForm = styled.form`
  width: 100%;
  height: 34rem;
  padding: 2rem 0;
  border-top: 1px solid ${({ theme }) => theme.color.border};
  border-bottom: 1px solid ${({ theme }) => theme.color.border};
  display: flex;
  flex-direction: column;
  justify-content: space-between;
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

export const StyleButton = styled(Button)``;
