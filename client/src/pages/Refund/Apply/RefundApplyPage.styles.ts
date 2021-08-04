import styled from 'styled-components';
import Template from '../../../components/@atom/Template/Template';
import PALETTE from '../../../constants/palette';

export const RefundApplyPageTemplate = styled(Template)`
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  min-height: 50rem;
`;

export const CautionContainer = styled.div`
  font-size: 0.75rem;
  text-align: center;
  line-height: 1rem;
  color: ${PALETTE.GRAY_400};
  position: absolute;
  bottom: 4.5rem;
`;
