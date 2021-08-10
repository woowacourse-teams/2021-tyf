import { Meta, Story } from '@storybook/react';

import Select, { SelectProps } from './Select';

export default {
  title: 'components/atom/Select',
  component: Select,
  argTypes: {},
} as Meta;

const Template: Story<SelectProps> = (args) => <Select {...args}></Select>;

export const Default = Template.bind({});

Default.args = {
  selectHeader: '등록할 계좌의 은행을 선택해주세요.',
  selectOptions: ['국민', '신한', '우리', '하나', '케이뱅크', '카카오뱅크'],
  value: null,
  onChange: () => {
    alert('값이 변경되었습니다.');
  },
};
