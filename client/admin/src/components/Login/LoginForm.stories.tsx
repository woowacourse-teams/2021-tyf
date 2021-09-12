import { Meta, Story } from '@storybook/react';

import LoginForm from './LoginForm';

export default {
  title: 'components/LoginForm',
  component: LoginForm,
} as Meta;

const Template: Story = (args) => <LoginForm {...args} />;

export const Default = Template.bind({});
