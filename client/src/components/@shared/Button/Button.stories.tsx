import Button, { ButtonProps } from './Button';
import { Meta, Story } from '@storybook/react';

export default {
  title: 'Commons/Button',
  component: Button,
  argTypes: {},
} as Meta;

const Template: Story<ButtonProps> = (args) => <Button {...args}>버튼</Button>;

export const Default = Template.bind({});
