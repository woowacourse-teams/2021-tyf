import { Story } from '@storybook/react';
import SettlementInfo from './MobileSettlementInfo';

export default {
  title: 'components/settlement/info/mobile',
};

const Template: Story = (args) => <SettlementInfo {...args} />;

export const Default = Template.bind({});
