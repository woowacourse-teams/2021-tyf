import { Meta, Story } from '@storybook/react';
import MessageForm from './DonationMessageForm';

export default {
  title: 'components/donation/messageForm',
} as Meta;

const Template: Story = (args) => <MessageForm {...args} />;

export const Default = Template.bind({});
