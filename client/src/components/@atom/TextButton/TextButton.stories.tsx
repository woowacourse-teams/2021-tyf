import { Meta, Story } from '@storybook/react';
import TextButton from './TextButton.styles';

export default {
  title: 'components/atom/TextButton',
} as Meta;

const Template: Story = (args) => <TextButton {...args}>텍스트 버튼</TextButton>;

export const Default = Template.bind({});
