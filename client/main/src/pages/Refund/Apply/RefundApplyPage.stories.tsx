import { Meta, Story } from '@storybook/react';

import RefundApplyPage from './RefundApplyPage';

export default {
  title: 'pages/refund/apply',
} as Meta;

const Template: Story = (args) => <RefundApplyPage {...args} />;

export const Default = Template.bind({});
