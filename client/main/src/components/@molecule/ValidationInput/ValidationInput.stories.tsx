import { Meta, Story } from '@storybook/react';
import { useState } from 'react';

import ValidationInput, { ValidationInputProps } from './ValidationInput';

export default {
  title: 'components/molecule/ValidationInput',
  component: ValidationInput,
  argTypes: {},
} as Meta;

const Template: Story<ValidationInputProps> = (args) => {
  const [value, setValue] = useState('');
  return (
    <ValidationInput value={value} onChange={({ target }) => setValue(target.value)} {...args} />
  );
};

export const Default = Template.bind({});

Default.args = {
  isSuccess: false,
  successMessage: '성공했습니다',
  failureMessage: '실패했습니다',
  placeholder: '입력해주세요',
};
