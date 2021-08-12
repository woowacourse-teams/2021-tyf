import styled from 'styled-components';
import Button from '../../@atom/Button/Button.styles';
import SubTitle from '../../@atom/SubTitle/SubTitle.styles';

export const StyledSettlementRegisterPendingInfo = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 5rem 0;

  ${SubTitle} {
    margin-bottom: 5rem;
  }

  ${Button} {
    width: 20rem;
  }
`;
