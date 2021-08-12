import { Meta, Story } from '@storybook/react';

import SettlementRegisterForm from './SettlementRegisterForm';

export default {
  title: 'components/settlement/SettlementRegisterForm',
} as Meta;

const Template: Story = (args) => <SettlementRegisterForm {...args} />;

export const Default = Template.bind({});

Default.args = {};
