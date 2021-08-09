import { Select, StyledSelectBox } from './SelectBox.styles';

const SelectBox = () => {
  return (
    <StyledSelectBox>
      <Select>
        <option>a</option>
        <option>b</option>
        <option>c</option>
        <option>d</option>
      </Select>
    </StyledSelectBox>
  );
};

export default SelectBox;
