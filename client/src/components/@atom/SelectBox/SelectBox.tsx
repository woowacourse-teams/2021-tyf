import { useState } from 'react';
import { SIZE } from '../../../constants/device';
import { useWindowResize } from '../../../utils/useWindowResize';

import {
  DropDownList,
  ListItem,
  ModalTransition,
  OptionModal,
  StyledSelectBox,
} from './SelectBox.styles';

export interface SelectBoxProps {
  selectHeader: string;
  selectOptions: string[];
  selected: string | null;
  setSelected: (account: string) => void;
}

const SelectBox = ({ selectHeader, selectOptions, selected, setSelected }: SelectBoxProps) => {
  const { windowWidth } = useWindowResize();
  const [isOpen, setIsOpen] = useState(false);

  const toggleOptions = () => {
    setIsOpen(!isOpen);
  };

  const onOptionClicked = (value: string) => {
    setSelected(value);
  };

  const onClose = () => {
    setIsOpen(false);
  };
  const isDesktop = windowWidth > SIZE.DESKTOP_LARGE;

  return (
    <StyledSelectBox onClick={toggleOptions}>
      {selected ?? selectHeader}
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

export default SelectBox;
