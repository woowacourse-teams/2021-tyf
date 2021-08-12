import { Meta, Story } from '@storybook/react';
import RefundCertificationForm from './RefundCertificationForm';

export default {
  title: 'components/refund/CertificationForm',
  component: RefundCertificationForm,
} as Meta;

const Template: Story = (args) => <RefundCertificationForm {...args} />;

export const Default = Template.bind({});
