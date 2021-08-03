import { Meta, Story } from '@storybook/react';
import MobileCreatorList from './MobileCreatorList';

export default {
  title: 'components/Main/CreatorList/mobile',
} as Meta;

const Template: Story = (args) => <MobileCreatorList {...args} />;

export const Default = Template.bind({});
