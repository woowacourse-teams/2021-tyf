import { Story } from '@storybook/react';
import SettlementInfo from './DesktopSettlementInfo';

export default {
  title: 'components/settlement/info/desktop',
};

const Template: Story = (args) => <SettlementInfo {...args} />;

export const Default = Template.bind({});
