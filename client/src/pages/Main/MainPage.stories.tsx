import { Meta, Story } from '@storybook/react';
import MainPage from './MainPage';

export default {
  title: 'pages/main',
} as Meta;

const Template: Story = (args) => <MainPage {...args} />;

export const Default = Template.bind({});
