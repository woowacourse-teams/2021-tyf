import { Meta, Story } from '@storybook/react';

import DonationForm from './DonationForm';

export default {
  title: 'components/donation/donationForm',
} as Meta;

const Template: Story = (args) => <DonationForm {...args} />;

export const Default = Template.bind({});
