import { Meta, Story } from '@storybook/react';

import SelectBox from './SelectBox';

export default {
  title: 'components/atom/SelectBox',
  component: SelectBox,
  argTypes: {},
} as Meta;

const Template: Story = (args) => <SelectBox {...args}></SelectBox>;

export const Default = Template.bind({});
