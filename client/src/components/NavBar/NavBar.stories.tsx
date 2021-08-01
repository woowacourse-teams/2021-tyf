import { Meta, Story } from '@storybook/react';

import NavBar from './NavBar';

export default {
  title: 'components/NavBar',
  component: NavBar,
} as Meta;

const Template: Story = (args) => <NavBar {...args} />;

export const Default = Template.bind({});
