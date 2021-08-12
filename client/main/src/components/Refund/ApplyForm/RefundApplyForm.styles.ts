import styled from 'styled-components';

import Input from '../../@atom/Input/Input.styles';
import SubTitle from '../../@atom/SubTitle/SubTitle.styles';
import Title from '../../@atom/Title/Title.styles';

export const StyledRefundApplyForm = styled.form`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  width: 100%;

  ${Title} {
    margin-bottom: 4.5rem;
  }

  ${SubTitle}:nth-of-type(1) {
    margin-bottom: 2rem;
    align-self: flex-start;
  }

  ${Input}:nth-of-type(1) {
    margin-bottom: 3rem;
  }
`;
