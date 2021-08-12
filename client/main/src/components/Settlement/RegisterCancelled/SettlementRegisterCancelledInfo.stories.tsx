import { Meta, Story } from '@storybook/react';
import SettlementRegisterCancelledInfo from './SettlementRegisterCancelledInfo';

export default {
  title: 'components/settlement/SettlementRegisterCancelledInfo',
} as Meta;

const Template: Story = (args) => <SettlementRegisterCancelledInfo {...args} />;

export const Default = Template.bind({});
