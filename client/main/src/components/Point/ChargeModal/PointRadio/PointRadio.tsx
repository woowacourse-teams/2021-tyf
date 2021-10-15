import React, { InputHTMLAttributes } from 'react';
import { StyledPointSelect } from './PointRadio.styles';

interface PointRadioProps extends InputHTMLAttributes<HTMLInputElement> {
  children: React.ReactNode;
}

const PointRadio = ({ children, id, value }: PointRadioProps) => {
  return (
    <StyledPointSelect>
      <input type="radio" name="point" id={id} value={value} />
      <label htmlFor={id}>{children}</label>
    </StyledPointSelect>
  );
};

export default PointRadio;
