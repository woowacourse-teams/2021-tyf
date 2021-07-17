import { FC, HTMLAttributes } from 'react';
import { useParams } from 'react-router';

import { ParamTypes } from '../../../App';
import useCreator from '../../../service/hooks/useCreator';
import useCreatorDonations from '../../../service/hooks/useCreatorDonations';
import { Donation } from '../../../types';
import {
  CommentsContainer,
  CommentsList,
  CommentsListItem,
  CommentsTitle,
  Divider,
  ItemContent,
  ItemInfo,
  ShowMoreButton,
} from './Comments.styles';

const Comments: FC<HTMLAttributes<HTMLElement>> = () => {
  const { creatorId } = useParams<ParamTypes>();
  const { nickname } = useCreator(creatorId);
  const { donationList } = useCreatorDonations(creatorId);

  return (
    <CommentsContainer>
      <CommentsTitle>{nickname}님을 응원하는 사람들</CommentsTitle>
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
      <ShowMoreButton>더보기</ShowMoreButton>
    </CommentsContainer>
  );
};

export default Comments;
