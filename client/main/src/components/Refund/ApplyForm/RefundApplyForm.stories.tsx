import { Meta, Story } from '@storybook/react';
import RefundApplyForm from './RefundApplyForm';

export default {
  title: 'components/refund/ApplyForm',
} as Meta;

const Template: Story = (args) => <RefundApplyForm {...args} />;

export const Default = Template.bind({});
