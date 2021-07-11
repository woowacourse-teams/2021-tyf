import { FC } from 'react';
import { ModalInner, ModalOuter } from './Modal.styles';

interface ModalType {
  children: React.ReactNode;
  className?: string;
}

const Modal: FC<ModalType> = ({ children, className }) => {
  return (
    <ModalOuter>
      <ModalInner className={className}>{children}</ModalInner>
    </ModalOuter>
  );
};

export default Modal;
