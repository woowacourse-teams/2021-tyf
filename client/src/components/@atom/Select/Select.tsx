import { useState } from 'react';
import { SIZE } from '../../../constants/device';
import useScrollLock from '../../../utils/useScrollLock';
import { useWindowResize } from '../../../utils/useWindowResize';

import {
  DropDownList,
  ListItem,
  ModalTransition,
  OptionModal,
  StyledSelectBox,
} from './Select.styles';

export interface SelectProps {
  selectHeader: string;
  selectOptions: string[];
  value: string | null;
  onChange: (account: string) => void;
}

const Select = ({ selectHeader, selectOptions, value, onChange }: SelectProps) => {
  const { windowWidth } = useWindowResize();
  const [isOpen, setIsOpen] = useState(false);

  useScrollLock();

  const toggleOptions = () => {
    setIsOpen(!isOpen);
  };

  const onOptionClicked = (optionValue: string) => {
    onChange(optionValue);
  };

  const onClose = () => {
    setIsOpen(false);
  };
  const isDesktop = windowWidth > SIZE.DESKTOP_LARGE;

  return (
    <StyledSelectBox onClick={toggleOptions}>
      {value ?? selectHeader}
      {isOpen &&
        (isDesktop ? (
          <DropDownList>
            {selectOptions.map((option, index) => (
              <ListItem onClick={() => onOptionClicked(option)} key={index}>
                {option}
              </ListItem>
            ))}
          </DropDownList>
        ) : (
          <OptionModal onClose={onClose}>
            <ModalTransition>
              <DropDownList>
                {selectOptions.map((option, index) => (
                  <ListItem onClick={() => onOptionClicked(option)} key={index}>
                    {option}
                  </ListItem>
                ))}
              </DropDownList>
            </ModalTransition>
          </OptionModal>
        ))}
    </StyledSelectBox>
  );
};

export default Select;
