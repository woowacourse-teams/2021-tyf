import { Meta, Story } from '@storybook/react';
import StatisticsPage from './StatisticsPage';

export default {
  title: 'pages/creator/statistic',
} as Meta;

const Template: Story = (args) => <StatisticsPage {...args} />;

export const Default = Template.bind({});
