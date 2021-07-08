import { Meta, Story } from '@storybook/react';
import IconButton, { IconButtonProps } from './IconButton';

export default {
  title: 'components/atom/IconButton',
  component: IconButton,
} as Meta;

const Template: Story<IconButtonProps> = (args) => <IconButton {...args} />;

export const Default = Template.bind({});

Default.args = {
  src: 'https://dummyimage.com/40x40/000/fff',
};
