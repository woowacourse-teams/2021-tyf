import { Meta, Story } from '@storybook/react';

import MessagePage from './DonationMessagePage';

export default {
  title: 'pages/donation/message',
} as Meta;

const Template: Story = (args) => <MessagePage {...args} />;

export const Default = Template.bind({});
