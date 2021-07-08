import { HTMLAttributes, FC } from 'react';
import { InnerTemplate, StyledTemplate } from './Template.styles';

export type TemplateProps = HTMLAttributes<HTMLDivElement>;

const Template: FC<TemplateProps> = ({ children, className, ...props }) => {
  return (
    <StyledTemplate {...props}>
      <InnerTemplate className={className}>{children}</InnerTemplate>
    </StyledTemplate>
  );
};

export default Template;
