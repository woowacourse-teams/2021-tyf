import { Meta, Story } from '@storybook/react';
import Menu, { MenuProps } from './Menu';

export default {
  title: 'components/menu',
  args: {
    onClose: () => {
      return;
    },
  },
} as Meta;

const Template: Story<MenuProps> = (args) => <Menu {...args} />;

export const Default = Template.bind({});
