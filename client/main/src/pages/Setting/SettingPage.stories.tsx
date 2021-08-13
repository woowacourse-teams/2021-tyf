import { Meta, Story } from '@storybook/react';
import SettingPage from './SettingPage';

export default {
  title: 'pages/setting',
} as Meta;

const Template: Story = (args) => <SettingPage {...args} />;

export const Default = Template.bind({});
