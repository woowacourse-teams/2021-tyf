import { Meta, Story } from '@storybook/react';
import AuthPage from './RegisterAuthPage';

export default {
  title: 'pages/register/auth',
} as Meta;

const Template: Story = (args) => <AuthPage {...args} />;

export const Default = Template.bind({});
