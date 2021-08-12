import { Meta, Story } from '@storybook/react';

import UserSettingForm from './UserSettingForm';

export default {
  title: 'components/setting/UserSettingForm',
  component: UserSettingForm,
} as Meta;

const Template: Story = (args) => <UserSettingForm {...args} />;

export const Default = Template.bind({});
