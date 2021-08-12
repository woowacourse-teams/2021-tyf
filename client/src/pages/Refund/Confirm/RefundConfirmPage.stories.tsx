import { Meta, Story } from '@storybook/react';

import RefundPage from './RefundConfirmPage';

export default {
  title: 'pages/refund/refund',
} as Meta;

const Template: Story = (args) => <RefundPage {...args} />;

export const Default = Template.bind({});
