import { Meta, Story } from '@storybook/react';
import SettlementPage from './SettlementRegisterPage';

export default {
  title: 'pages/settlement/register',
} as Meta;

const Template: Story = (args) => <SettlementPage {...args} />;

export const Default = Template.bind({});
