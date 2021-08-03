import { Meta, Story } from '@storybook/react';

import DonatorPaymentPage from './DonationPaymentPage';

export default {
  title: 'pages/donation/payment',
} as Meta;

const Template: Story = (args) => <DonatorPaymentPage {...args} />;

export const Default = Template.bind({});
