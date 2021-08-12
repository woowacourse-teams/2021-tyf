import { Meta, Story } from '@storybook/react';
import LoginPage from './LoginPage';

export default {
  title: 'pages/login',
  component: LoginPage,
} as Meta;

const Template: Story = (args) => <LoginPage {...args} />;

export const Default = Template.bind({});
