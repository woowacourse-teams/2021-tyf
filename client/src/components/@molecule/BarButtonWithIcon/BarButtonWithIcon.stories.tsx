import { Meta, Story } from '@storybook/react';
import BarButtonWithIcon, { BarButtonWithIconProps } from './BarButtonWithIcon';
import GoogleLogo from '../../../assets/icons/google.svg';

export default {
  title: 'components/molecule/BarButtonWithIcon',
  component: BarButtonWithIcon,
} as Meta;

const Template: Story<BarButtonWithIconProps> = (args) => <BarButtonWithIcon {...args} />;

export const Default = Template.bind({});

Default.args = {
  src: GoogleLogo,
  alt: 'google_logo',
  children: '버튼텍스트',
};
