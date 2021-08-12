import { Meta, Story } from '@storybook/react';
import CreatorPage from './CreatorPage';

export default {
  title: 'pages/creator/creator',
} as Meta;

const Template: Story = (args) => <CreatorPage {...args} />;

export const Default = Template.bind({});
