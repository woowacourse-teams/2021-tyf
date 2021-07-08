import { TextareaHTMLAttributes } from 'react';
import { VFC } from 'react';

import { StyledTextarea } from './Textarea.styles';

export type TextareaProps = TextareaHTMLAttributes<HTMLTextAreaElement>;

const Textarea: VFC<TextareaProps> = (props) => {
  return <StyledTextarea {...props} />;
};

export default Textarea;
