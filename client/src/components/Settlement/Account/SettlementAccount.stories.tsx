import { Meta, Story } from '@storybook/react';

import SettlementAccount, { SettlementAccountProps } from './SettlementAccount';

export default {
  title: 'components/settlement/SettlementAccount',
  component: SettlementAccount,
} as Meta;

const Template: Story<SettlementAccountProps> = (args) => <SettlementAccount {...args} />;

export const Default = Template.bind({});

Default.args = {
  onClose: () => {
    alert('모달 닫기');
  },
};
