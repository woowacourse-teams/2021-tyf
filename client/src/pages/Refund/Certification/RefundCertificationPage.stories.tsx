import { Meta, Story } from '@storybook/react';
import RefundCertificationPage from './RefundCertificationPage';

export default {
  title: 'pages/refund/CertificationPage',
} as Meta;

const Template: Story = (args) => <RefundCertificationPage {...args} />;

export const Default = Template.bind({});
