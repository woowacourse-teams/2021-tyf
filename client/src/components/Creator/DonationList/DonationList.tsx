import { useState } from 'react';
import { useParams } from 'react-router';

import { ParamTypes } from '../../../App';
import useCreator from '../../../service/hooks/useCreator';
import useCreatorDonations from '../../../service/hooks/useCreatorDonations';
import {
  DonationListContainer,
  CommentsList,
  CommentsListItem,
  DonationListTitle,
  Divider,
  ItemContent,
  ItemInfo,
  ShowMoreButton,
} from './DonationList.styles';

interface Props {
  isAdmin: boolean;
}

const DonationList = ({ isAdmin }: Props) => {
  const [donationListPageQuery, setDonationListPageQuery] = useState(1);

  const { creatorId } = useParams<ParamTypes>();
  const { nickname } = useCreator(creatorId);
  const { donationList } = useCreatorDonations({ isAdmin, creatorId, page: donationListPageQuery });

  return (
    <DonationListContainer>
      <DonationListTitle>{nickname}님을 응원하는 사람들</DonationListTitle>
      <CommentsList>
        {donationList.map(({ donationId, name, message, amount }) => (
          <CommentsListItem key={donationId}>
            <ItemInfo>
              <span>
                {name} <Divider>|</Divider> {amount.toLocaleString('en-us')}원
              </span>
              <span>21.08.02</span>
            </ItemInfo>
            <ItemContent>{message}</ItemContent>
          </CommentsListItem>
        ))}
      </CommentsList>
      {isAdmin && <ShowMoreButton>더보기</ShowMoreButton>}
    </DonationListContainer>
  );
};

export default DonationList;
