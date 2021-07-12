import { Meta, Story } from '@storybook/react';

import Textarea, { TextareaProps } from './Textarea';

export default {
  title: 'components/atom/Textarea',
  component: Textarea,
  argTypes: {},
} as Meta;

const Template: Story<TextareaProps> = (args) => <Textarea {...args}></Textarea>;

export const Default = Template.bind({});

Default.args = {
  placeholder: '입력해주세요',
};
