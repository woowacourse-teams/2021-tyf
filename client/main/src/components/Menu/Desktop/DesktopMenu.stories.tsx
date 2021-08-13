import { Meta, Story } from '@storybook/react';
import DesktopMenu, { DesktopMenuProps } from './DesktopMenu';

export default {
  title: 'components/menu/desktop',
  args: {
    onClose: () => {
      return;
    },
  },
} as Meta;

const Template: Story<DesktopMenuProps> = (args) => <DesktopMenu {...args} />;

export const Default = Template.bind({});
