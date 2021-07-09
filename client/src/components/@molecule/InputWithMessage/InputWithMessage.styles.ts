import styled from 'styled-components';
import PALETTE from '../../../constants/palette';

export const StyledInputWithMessage = styled.div`
  width: 100%;
  min-height: 4.25rem;
`;

const Message = styled.div`
  margin-top: 0.5rem;
  padding: 0 1rem;
  font-size: 0.75rem;
`;

export const SuccessMessage = styled(Message)`
  color: ${PALETTE.GREEN_700};
`;

export const FailureMessage = styled(Message)`
  color: ${PALETTE.CORAL_700};
`;
