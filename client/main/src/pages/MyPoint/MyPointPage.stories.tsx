import { Meta, Story } from '@storybook/react';
import MyPointPage from './MyPointPage';

export default {
  title: 'pages/mypoint',
} as Meta;

const Template: Story = (args) => <MyPointPage {...args} />;

export const Default = Template.bind({});
