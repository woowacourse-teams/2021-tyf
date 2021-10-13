import { Meta, Story } from '@storybook/react';
import { InputHTMLAttributes } from 'react';

import Checkbox from './Checkbox.styles';

export default {
  title: 'components/atom/Checkbox',
  component: Checkbox,
  argTypes: {},
} as Meta;

const Template: Story<InputHTMLAttributes<HTMLInputElement>> = (args) => <Checkbox {...args} />;

export const Default = Template.bind({});

Default.args = {
  checked: false,
};
