import { Meta, Story } from '@storybook/react';
import { userInfoMock } from '../../../mock/mockData';
import URLShareSelectModal, {
  URLShareSelectModalProps,
} from '../URLShareSelectModal/URLShareSelectModal';

export default {
  title: 'components/URLShareSelectModal',
  args: {
    onClose: () => {
      return;
    },
  },
} as Meta;

const Template: Story<URLShareSelectModalProps> = (args) => <URLShareSelectModal {...args} />;

export const Default = Template.bind({});

Default.args = {
  userInfo: userInfoMock,
};
