import { InputHTMLAttributes, VFC } from 'react';

import { StyledCheckbox } from './Checkbox.styles';

export type CheckboxProps = InputHTMLAttributes<HTMLInputElement>;

const Checkbox: VFC<CheckboxProps> = (props) => {
  return <StyledCheckbox type="checkbox" {...props} />;
};

export default Checkbox;
