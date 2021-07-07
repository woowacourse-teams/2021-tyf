import styled from 'styled-components';
import PALETTE from '../../../constants/palette';

const StyledInputWithMessage = styled.div``;

const Message = styled.div`
  margin-top: 0.5rem;
  padding: 0 1rem;
  font-size: 0.75rem;
`;

const SuccessMessage = styled(Message)`
  color: ${PALETTE.GREEN_700};
`;

const FailureMessage = styled(Message)`
  color: ${PALETTE.CORAL_700};
`;

export { StyledInputWithMessage, SuccessMessage, FailureMessage };
