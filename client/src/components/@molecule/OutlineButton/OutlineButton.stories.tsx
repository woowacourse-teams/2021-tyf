import { Meta, Story } from '@storybook/react';

import { ButtonProps } from '../../@atom/Button/Button';
import OutlineButton from './OutlineButton';

export default {
  title: 'components/molecule/OutlineButton',
  component: OutlineButton,
  argTypes: {},
} as Meta;

const Template: Story<ButtonProps> = (args) => <OutlineButton {...args}>버튼</OutlineButton>;

export const Default = Template.bind({});

Default.args = {
  disabled: false,
};
