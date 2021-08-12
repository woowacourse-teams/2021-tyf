import { Meta, Story } from '@storybook/react';

import Spinner from './Spinner';

export default {
  title: 'components/Spinner',
  component: Spinner,
} as Meta;

const Template: Story = () => <Spinner />;

export const Default = Template.bind({});
