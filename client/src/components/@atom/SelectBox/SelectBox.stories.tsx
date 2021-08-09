import { Meta, Story } from '@storybook/react';

import SelectBox, { SelectBoxProps } from './SelectBox';

export default {
  title: 'components/atom/SelectBox',
  component: SelectBox,
  argTypes: {},
} as Meta;

const Template: Story<SelectBoxProps> = (args) => <SelectBox {...args}></SelectBox>;

export const Default = Template.bind({});

Default.args = {
  selectHeader: '등록할 계좌의 은행을 선택해주세요.',
  selectOptions: ['국민', '신한', '우리', '하나', '케이뱅크', '카카오뱅크'],
  selected: null,
  // eslint-disable-next-line @typescript-eslint/no-empty-function
  setSelected: () => {},
};
