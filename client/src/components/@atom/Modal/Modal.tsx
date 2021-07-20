import { HTMLAttributes, MouseEvent } from 'react';
import useScrollLock from '../../../utils/useScrollLock';

import { ModalInner, ModalOuter } from './Modal.styles';

export interface ModalProps extends HTMLAttributes<HTMLDivElement> {
  onClose?: () => void;
}

const Modal = ({ children, className, onClose }: ModalProps) => {
  const onClickModalOuter = (event: MouseEvent<HTMLDivElement>) => {
    if (event.target !== event.currentTarget) return;

    if (onClose) onClose();
  };

  useScrollLock();

  return (
    <ModalOuter onClick={onClickModalOuter}>
      <ModalInner className={className}>{children}</ModalInner>
    </ModalOuter>
  );
};

export default Modal;
