import { Meta, Story } from '@storybook/react';

import DonationAmountPage from './DonationAmountPage';

export default {
  title: 'pages/donation/amount',
} as Meta;

const Template: Story = (args) => <DonationAmountPage {...args} />;

export const Default = Template.bind({});
