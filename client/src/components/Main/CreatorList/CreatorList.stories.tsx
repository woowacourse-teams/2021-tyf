import { Meta, Story } from '@storybook/react';

import CreatorList from './CreatorList';

export default {
  title: 'components/Main/CreatorList',
  component: CreatorList,
} as Meta;

const Template: Story = (args) => <CreatorList {...args} />;

export const Default = Template.bind({});
