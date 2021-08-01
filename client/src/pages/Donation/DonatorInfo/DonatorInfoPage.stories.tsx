import { Meta, Story } from '@storybook/react';

import DonatorInfoPage from './DonatorInfoPage';

export default {
  title: 'pages/donation/donatorInfo',
} as Meta;

const Template: Story = (args) => <DonatorInfoPage {...args} />;

export const Default = Template.bind({});
