import { Meta, Story } from '@storybook/react';
import { useState } from 'react';

import InputWithMessage, { InputWithMessageProps } from './InputWithMessage';

export default {
  title: 'components/molecule/InputWithMessage',
  component: InputWithMessage,
  argTypes: {},
} as Meta;

const Template: Story<InputWithMessageProps> = (args) => {
  const [value, setValue] = useState('');
  return (
    <InputWithMessage value={value} onChange={({ target }) => setValue(target.value)} {...args} />
  );
};

export const Default = Template.bind({});

Default.args = {
  isSuccess: false,
  successMessage: '성공했습니다',
  failureMessage: '실패했습니다',
  placeholder: '입력해주세요',
};
