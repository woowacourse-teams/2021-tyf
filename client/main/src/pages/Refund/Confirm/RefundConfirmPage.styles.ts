import styled from 'styled-components';
import Button from '../../../components/@atom/Button/Button.styles';
import Template from '../../../components/@atom/Template/Template';

export const RefundConfirmPageTemplate = styled(Template)`
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  min-height: 50rem;
`;

export const ButtonContainer = styled.div`
  width: 100%;
  position: absolute;
  bottom: 2rem;

  ${Button}:nth-of-type(1) {
    margin-bottom: 2rem;
  }
`;
