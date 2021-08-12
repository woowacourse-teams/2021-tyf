import { Meta, Story } from '@storybook/react';
import SuccessPage from './DonationSuccessPage';

export default {
  title: 'pages/donation/success',
} as Meta;

const Template: Story = (args) => <SuccessPage {...args} />;

export const Default = Template.bind({});
