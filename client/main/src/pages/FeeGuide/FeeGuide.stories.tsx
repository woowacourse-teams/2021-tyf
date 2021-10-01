import { Meta, Story } from '@storybook/react';
import FeeGuide from './FeeGuide';

export default {
  title: 'pages/FeeGuide',
} as Meta;

const Template: Story = (args) => <FeeGuide {...args} />;

export const Default = Template.bind({});
