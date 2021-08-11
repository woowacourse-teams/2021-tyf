import { HTMLAttributes } from 'react';
import { InnerTemplate, StyledTemplate } from './Template.styles';

export interface TemplateProps extends HTMLAttributes<HTMLDivElement> {}

const Template = ({ children, className, ...props }: TemplateProps) => {
  return (
    <StyledTemplate {...props}>
      <InnerTemplate className={className}>{children}</InnerTemplate>
    </StyledTemplate>
  );
};

export default Template;
