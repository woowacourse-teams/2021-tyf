import { useState } from 'react';

const useModal = () => {
  const [isOpen, setIsOpen] = useState(false);

  const closeModal = () => {
    setIsOpen(false);
  };

  const openModal = () => {
    setIsOpen(true);
  };

  const toggleModal = () => {
    setIsOpen(!isOpen);
  };

  return { isOpen, closeModal, openModal, toggleModal };
};

export default useModal;
