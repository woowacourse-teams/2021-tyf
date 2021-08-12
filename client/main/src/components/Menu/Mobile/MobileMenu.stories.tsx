import { Meta, Story } from '@storybook/react';
import MobileMenu, { MobileMenuProps } from './MobileMenu';

export default {
  title: 'components/menu/mobile',
  args: {
    onClose: () => {
      return;
    },
  },
} as Meta;

const Template: Story<MobileMenuProps> = (args) => <MobileMenu {...args} />;

export const Default = Template.bind({});
