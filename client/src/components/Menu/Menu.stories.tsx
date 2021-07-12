import { Meta, Story } from '@storybook/react';
import Menu from './Menu';

export default {
  title: 'components/menu',
} as Meta;

const Template: Story = (args) => <Menu {...args} />;

export const Default = Template.bind({});
