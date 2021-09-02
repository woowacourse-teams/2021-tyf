import { Meta, Story } from '@storybook/react';

import BankAccount from './BankAccount';

export default {
  title: 'components/BankAccount',
  component: BankAccount,
} as Meta;

const Template: Story = (args) => <BankAccount {...args} />;

export const Default = Template.bind({});
