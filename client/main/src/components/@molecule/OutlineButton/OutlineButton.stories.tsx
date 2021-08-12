import { Meta, Story } from '@storybook/react';

import OutlineButton from './OutlineButton.styles';

export default {
  title: 'components/molecule/OutlineButton',
  component: OutlineButton,
  argTypes: {},
} as Meta;

const Template: Story = (args) => <OutlineButton {...args}>버튼</OutlineButton>;

export const Default = Template.bind({});

Default.args = {
  disabled: false,
};
