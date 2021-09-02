import { Meta, Story } from '@storybook/react';

import Settlement from './Settlement';

export default {
  title: 'components/Settlement',
  component: Settlement,
} as Meta;

const Template: Story = (args) => <Settlement {...args} />;

export const Default = Template.bind({});
