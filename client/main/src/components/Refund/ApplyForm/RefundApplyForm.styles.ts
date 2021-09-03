import styled from 'styled-components';

import PALETTE from '../../../constants/palette';
import Input from '../../@atom/Input/Input.styles';
import SubTitle from '../../@atom/SubTitle/SubTitle.styles';
import Title from '../../@atom/Title/Title.styles';

export const StyledRefundApplyForm = styled.form`
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-direction: column;
  width: 100%;
  height: 25rem;

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

export const CautionContainer = styled.div`
  margin-top: 3rem;
  font-size: 0.75rem;
  text-align: center;
  line-height: 1rem;
  color: ${PALETTE.GRAY_400};

  bottom: 4.5rem;
`;
