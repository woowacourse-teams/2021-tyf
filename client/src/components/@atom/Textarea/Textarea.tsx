import { TextareaHTMLAttributes } from 'react';

import { StyledTextarea } from './Textarea.styles';

export interface TextareaProps extends TextareaHTMLAttributes<HTMLTextAreaElement> {}

const Textarea = (props: TextareaProps) => {
  return <StyledTextarea {...props} />;
};

export default Textarea;
