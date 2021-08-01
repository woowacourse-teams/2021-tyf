import { Meta, Story } from '@storybook/react';

import DonationAmountForm, { DonationAmountFormProps } from './DonationAmountForm';

export default {
  title: 'components/donation/donationAmountForm',
} as Meta;

const Template: Story<DonationAmountFormProps> = (args) => <DonationAmountForm {...args} />;

export const Default = Template.bind({});
