import styled from 'styled-components';
import Container from '../@atom/Container/Container';

import Modal from '../@atom/Modal/Modal';
import OutlineButton from '../@molecule/OutlineButton/OutlineButton';

export const StyledModal = styled(Modal)`
  align-self: flex-end;
  width: 100vw;
  display: flex;
  flex-direction: column;
  padding: 0 1.5rem;
`;

export const ProfileContainer = styled(Container)`
  flex-direction: row;
  padding: 1.5rem 0.75rem;
  justify-content: space-between;
  border-bottom: 1px solid ${({ theme }) => theme.color.border};
`;

export const URLCopyButton = styled(OutlineButton)`
  width: 7.75rem;
  height: 2rem;
`;
