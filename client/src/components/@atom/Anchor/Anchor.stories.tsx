import Anchor, { AnchorProps } from './Anchor';

import { Meta, Story } from '@storybook/react';

export default {
  title: 'components/atom/Anchor',
  component: Anchor,
  argTypes: {},
} as Meta;

const Template: Story<AnchorProps> = (args) => (
  <Anchor href="#" {...args}>
    버튼
  </Anchor>
);

export const Default = Template.bind({});

Default.args = {};
