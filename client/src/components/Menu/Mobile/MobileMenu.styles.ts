import styled from 'styled-components';
import Anchor from '../../@atom/Anchor/Anchor';
import Container from '../../@atom/Container/Container.styles';
import Modal from '../../@atom/Modal/Modal';
import OutlineButton from '../../@molecule/OutlineButton/OutlineButton';

export const StyledModal = styled(Modal)`
  align-self: flex-end;
  width: 100vw;
  display: flex;
  flex-direction: column;
  padding: 0 1.5rem;
  border-radius: 0.625rem 0.625rem 0 0;
`;

export const ProfileContainer = styled(Container)`
  flex-direction: row;
  padding: 0.375rem;
  padding-top: 0.625rem;
  padding-bottom: 0.375rem;
  justify-content: space-between;
  border-bottom: 1px solid ${({ theme }) => theme.color.border};
`;

export const URLCopyButton = styled(OutlineButton)`
  width: 5.75rem;
  height: 1.75rem;
  font-size: 0.75rem;
  font-weight: 500;
`;

export const StyledAnchor = styled(Anchor)`
  height: 4rem;
  line-height: 4rem;
  font-size: 1.125rem;
  padding-left: 0.5rem;
`;
