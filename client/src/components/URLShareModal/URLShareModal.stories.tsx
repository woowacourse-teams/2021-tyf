import { Meta, Story } from '@storybook/react';
import { userInfoMock } from '../../mock/mockData';
import URLShareModal, { URLShareModalProps } from './URLShareModal';

export default {
  title: 'components/URLShareModal',
  args: {
    onClose: () => {
      return;
    },
  },
} as Meta;

const Template: Story<URLShareModalProps> = (args) => <URLShareModal {...args} />;

export const Default = Template.bind({});

Default.args = {
  userInfo: userInfoMock,
};
