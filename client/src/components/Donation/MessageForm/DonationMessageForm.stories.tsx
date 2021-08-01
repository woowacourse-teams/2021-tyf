import { Meta, Story } from '@storybook/react';

import MessageForm, { DonationMessageFormProps } from './DonationMessageForm';

export default {
  title: 'components/donation/messageForm',
} as Meta;

const Template: Story<DonationMessageFormProps> = (args) => <MessageForm {...args} />;

export const Default = Template.bind({});
