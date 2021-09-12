import { Story } from '@storybook/react';

import PointChargeModal, { PointChargeModalProps } from './PointChargeModal';

export default {
  title: 'components/Point/ChargeModal',
};

const Template: Story<PointChargeModalProps> = (args) => <PointChargeModal {...args} />;

export const Default = Template.bind({});
