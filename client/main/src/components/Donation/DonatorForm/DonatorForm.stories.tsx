import { Meta, Story } from '@storybook/react';
import DonatorForm, { DonatorFormProps } from './DonatorForm';

export default {
  title: 'components/donation/donatorForm',
} as Meta;

const Template: Story<DonatorFormProps> = (args) => <DonatorForm {...args} />;

export const Default = Template.bind({});
