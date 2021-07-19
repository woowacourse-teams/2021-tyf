import { FC, MouseEvent } from 'react';
import { ModalInner, ModalOuter } from './Modal.styles';

export interface ModalType {
  children: React.ReactNode;
  className?: string;
  onClose?: () => void;
}

const Modal: FC<ModalType> = ({ children, className, onClose }) => {
  const onClickModalOuter = (event: MouseEvent<HTMLDivElement>) => {
    if (event.target !== event.currentTarget) return;

    if (onClose) onClose();
  };

  return (
    <ModalOuter onClick={onClickModalOuter}>
      <ModalInner className={className}>{children}</ModalInner>
    </ModalOuter>
  );
};

export default Modal;
