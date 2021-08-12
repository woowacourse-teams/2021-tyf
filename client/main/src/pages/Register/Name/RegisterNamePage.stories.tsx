import { Meta, Story } from '@storybook/react';
import NamePage from './RegisterNamePage';

export default {
  title: 'pages/register/name',
} as Meta;

const Template: Story = (args) => <NamePage {...args} />;

export const Default = Template.bind({});
