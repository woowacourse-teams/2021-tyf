import { Meta, Story } from '@storybook/react';
import { creatorListMock } from '../../../mock/mockData';

import CreatorCard, { CreatorCardProps } from './CreatorCard';

export default {
  title: 'components/Main/CreatorCard',
  component: CreatorCard,
} as Meta;

const Template: Story<CreatorCardProps> = (args) => <CreatorCard {...args} />;

export const Default = Template.bind({});

Default.args = {
  creator: creatorListMock[0],
};
