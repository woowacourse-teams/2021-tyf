import { Meta, Story } from '@storybook/react';

import IconOutlineBarButton, { IconOutlineBarButtonProps } from './IconOutlineBarButton';
import GoogleLogo from '../../../assets/icons/google.svg';

export default {
  title: 'components/molecule/IconOutlineBarButton',
  component: IconOutlineBarButton,
} as Meta;

const Template: Story<IconOutlineBarButtonProps> = (args) => <IconOutlineBarButton {...args} />;

export const Default = Template.bind({});

Default.args = {
  src: GoogleLogo,
  alt: 'google_logo',
  children: '버튼텍스트',
};
