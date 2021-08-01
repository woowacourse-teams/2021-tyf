import { Meta, Story } from '@storybook/react';

import OutlineBarButtonWithIcon, {
  OutlineBarButtonWithIconProps,
} from './OutlineBarButtonWithIcon';
import GoogleLogo from '../../../assets/icons/google.svg';

export default {
  title: 'components/molecule/OutlineBarButtonWithIcon',
  component: OutlineBarButtonWithIcon,
} as Meta;

const Template: Story<OutlineBarButtonWithIconProps> = (args) => (
  <OutlineBarButtonWithIcon {...args} />
);

export const Default = Template.bind({});

Default.args = {
  src: GoogleLogo,
  alt: 'google_logo',
  children: '버튼텍스트',
};
