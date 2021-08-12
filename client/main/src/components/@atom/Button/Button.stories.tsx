import { Meta, Story } from '@storybook/react';

import Button from './Button.styles';

export default {
  title: 'components/atom/Button',
  component: Button,
  argTypes: {},
} as Meta;

const Template: Story = (args) => <Button {...args}>버튼</Button>;

export const Default = Template.bind({});

Default.args = {
  disabled: false,
};
