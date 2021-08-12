import { Meta, Story } from '@storybook/react';

import IconBarButton, { IconBarButtonProps } from './IconBarButton';
import GoogleLogo from '../../../assets/icons/google.svg';

export default {
  title: 'components/molecule/IconBarButton',
  component: IconBarButton,
} as Meta;

const Template: Story<IconBarButtonProps> = (args) => <IconBarButton {...args} />;

export const Default = Template.bind({});

Default.args = {
  src: GoogleLogo,
  alt: 'google_logo',
  children: '버튼텍스트',
};
