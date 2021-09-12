import { Meta, Story } from '@storybook/react';
import RefundInfo, { RefundInfoProps } from './RefundInfo';

export default {
  title: 'components/refund/Info',
  component: RefundInfo,
} as Meta;

const Template: Story<RefundInfoProps> = (args) => <RefundInfo {...args} />;

export const Default = Template.bind({});

Default.args = { refundAccessToken: 'sdfsfew' };
