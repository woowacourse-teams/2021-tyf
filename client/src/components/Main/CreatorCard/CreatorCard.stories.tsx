import { Meta, Story } from '@storybook/react';

import CreatorCard from './CreatorCard';

export default {
  title: 'components/Main/CreatorCard',
  component: CreatorCard,
} as Meta;

const Template: Story = (args) => <CreatorCard {...args} />;

export const Default = Template.bind({});
