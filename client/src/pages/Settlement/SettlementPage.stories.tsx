import { Meta, Story } from '@storybook/react';
import SettlementPage from './SettlementPage';

export default {
  title: 'pages/settlement',
} as Meta;

const Template: Story = (args) => <SettlementPage {...args} />;

export const Default = Template.bind({});
