import { Meta, Story } from '@storybook/react';

import DonationForm, { DonationFormProps } from './DonationForm';

export default {
  title: 'components/donation/donationForm',
} as Meta;

const Template: Story<DonationFormProps> = (args) => <DonationForm {...args} />;

export const Default = Template.bind({});
