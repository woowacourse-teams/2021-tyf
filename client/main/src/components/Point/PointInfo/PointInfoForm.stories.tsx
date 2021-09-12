import { Meta, Story } from '@storybook/react';

import PointInfoForm from './PointInfoForm';

export default {
  title: 'components/PointInfoForm',
  component: PointInfoForm,
} as Meta;

const Template: Story = (args) => <PointInfoForm {...args} />;

export const Default = Template.bind({});
