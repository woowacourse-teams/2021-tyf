import { Meta, Story } from '@storybook/react';

import ErrorFallback from './ErrorFallback';

export default {
  title: 'components/ErrorFallback',
  component: ErrorFallback,
} as Meta;

const Template: Story = (args) => <ErrorFallback {...args} />;

export const Default = Template.bind({});
