import { Meta, Story } from '@storybook/react';
import TermsPage from './RegisterTermsPage';

export default {
  title: 'pages/register/terms',
} as Meta;

const Template: Story = (args) => <TermsPage {...args} />;

export const Default = Template.bind({});
