import { InputHTMLAttributes } from 'react';

import { StyledCheckbox } from './Checkbox.styles';

export type CheckboxProps = InputHTMLAttributes<HTMLInputElement>;

const Checkbox = (props: CheckboxProps) => {
  return <StyledCheckbox type="checkbox" {...props} />;
};

export default Checkbox;
