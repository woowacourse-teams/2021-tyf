import { Meta, Story } from '@storybook/react';
import AddressPage from './RegisterAddressPage';

export default {
  title: 'pages/register/address',
} as Meta;

const Template: Story = (args) => <AddressPage {...args} />;

export const Default = Template.bind({});
