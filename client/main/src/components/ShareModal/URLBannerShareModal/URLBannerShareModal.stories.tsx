import { Meta, Story } from '@storybook/react';
import { userInfoMock } from '../../../mock/mockData';
import URLBannerShareModal, {
  URLBannerShareModalProps,
} from '../URLBannerShareModal/URLBannerShareModal';

export default {
  title: 'components/URLBannerShareModal',
  args: {
    onClose: () => {
      return;
    },
  },
} as Meta;

const Template: Story<URLBannerShareModalProps> = (args) => <URLBannerShareModal {...args} />;

export const Default = Template.bind({});

Default.args = {
  userInfo: userInfoMock,
};
