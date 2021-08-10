import { SIZE } from '../../../constants/device';
import useModal from '../../../utils/useModal';
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
  placeholder: string;
  selectOptions: string[];
  value: string | null;
  onChange: (account: string) => void;
}

const Select = ({ placeholder, selectOptions, value, onChange }: SelectProps) => {
  const { windowWidth } = useWindowResize();
  const { closeModal, isOpen, toggleModal } = useModal();

  const onOptionClicked = (optionValue: string) => onChange(optionValue);

  const isDesktop = windowWidth > SIZE.DESKTOP_LARGE;

  useScrollLock();

  return (
    <StyledSelectBox onClick={toggleModal}>
      {value ?? placeholder}
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
          <OptionModal onClose={closeModal}>
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
