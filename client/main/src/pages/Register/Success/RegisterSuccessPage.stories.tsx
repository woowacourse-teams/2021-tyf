import { Meta, Story } from '@storybook/react';
import SuccessPage from './RegisterSuccessPage';

export default {
  title: 'pages/register/success',
} as Meta;

const Template: Story = (args) => <SuccessPage {...args} />;

export const Default = Template.bind({});
