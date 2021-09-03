import { Meta, Story } from '@storybook/react';
import PointPage from './PointInfoPage';

export default {
  title: 'pages/point/info',
} as Meta;

const Template: Story = (args) => <PointPage {...args} />;

export const Default = Template.bind({});
