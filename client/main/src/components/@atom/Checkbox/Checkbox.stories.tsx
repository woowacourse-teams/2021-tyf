import { Meta, Story } from '@storybook/react';

import Checkbox, { CheckboxProps } from './Checkbox';

export default {
  title: 'components/atom/Checkbox',
  component: Checkbox,
  argTypes: {},
} as Meta;

const Template: Story<CheckboxProps> = (args) => <Checkbox {...args} />;

export const Default = Template.bind({});

Default.args = {
  checked: false,
};
