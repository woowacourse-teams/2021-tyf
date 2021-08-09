import { Meta, Story } from '@storybook/react';

import SettlementAccountForm from './SettlementAccountForm';

export default {
  title: 'components/settlement/SettlementAccountForm',
  component: SettlementAccountForm,
} as Meta;

const Template: Story = (args) => <SettlementAccountForm {...args} />;

export const Default = Template.bind({});
