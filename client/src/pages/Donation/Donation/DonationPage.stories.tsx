import { Meta, Story } from '@storybook/react';

import DonationPage from './DonationPage';

export default {
  title: 'pages/donation/donation',
} as Meta;

const Template: Story = (args) => <DonationPage {...args} />;

export const Default = Template.bind({});
