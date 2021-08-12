import { Meta, Story } from '@storybook/react';

import Anchor, { AnchorProps } from './Anchor';

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
