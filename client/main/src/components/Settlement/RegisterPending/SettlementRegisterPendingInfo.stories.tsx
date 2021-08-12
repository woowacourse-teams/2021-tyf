import { Meta, Story } from '@storybook/react';
import SettlementRegisterCancelledInfo from './SettlementRegisterPendingInfo';

export default {
  title: 'components/settlement/SettlementRegisterPendingInfo',
} as Meta;

const Template: Story = (args) => <SettlementRegisterCancelledInfo {...args} />;

export const Default = Template.bind({});
