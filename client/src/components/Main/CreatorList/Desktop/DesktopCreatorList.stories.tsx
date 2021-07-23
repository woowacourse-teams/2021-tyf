import { Meta, Story } from '@storybook/react';

import DesktopCreatorList from './DesktopCreatorList';

export default {
  title: 'components/Main/CreatorList/desktop',
} as Meta;

const Template: Story = (args) => <DesktopCreatorList {...args} />;

export const Default = Template.bind({});
