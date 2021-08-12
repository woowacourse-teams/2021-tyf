import { Meta, Story } from '@storybook/react';

import Input from './Input.styles';

export default {
  title: 'components/atom/Input',
  component: Input,
  argTypes: {},
} as Meta;

const Template: Story = (args) => <Input {...args}></Input>;

export const Default = Template.bind({});

Default.args = {
  placeholder: '입력해주세요',
};
