import { useParams } from 'react-router';

import { ParamTypes } from '../../../App';
import useCreator from '../../../service/hooks/useCreator';
import useDonationMessageList from '../../../service/hooks/useDonationMessageList';
import {
  DonationListContainer,
  CommentsList,
  CommentsListItem,
  DonationListTitle,
  Divider,
  ItemContent,
  ItemInfo,
  ItemDateInfo,
  ShowMoreButton,
  EmptyCommentsList,
} from './DonationMessageList.styles';

interface Props {
  isAdmin: boolean;
}

const DonationMessageList = ({ isAdmin }: Props) => {
  const { creatorId } = useParams<ParamTypes>();
  const { nickname } = useCreator(creatorId);
  const { donationList, showMoreDonationList, hasMoreList } = useDonationMessageList(
    isAdmin,
    creatorId
  );

  return (
    <DonationListContainer>
      <DonationListTitle>{nickname}님을 응원하는 사람들</DonationListTitle>
      {donationList.length > 0 ? (
        <>
          <CommentsList>
            {donationList.map(({ donationId, name, message, amount, createdAt }) => (
              <CommentsListItem key={donationId}>
                <ItemInfo>
                  <span>
                    {name} <Divider>|</Divider> {amount.toLocaleString('en-us')}원
                  </span>
                  <ItemDateInfo>{String(createdAt).slice(0, 10)}</ItemDateInfo>
                </ItemInfo>
                <ItemContent>{message}</ItemContent>
              </CommentsListItem>
            ))}
          </CommentsList>
          {hasMoreList && <ShowMoreButton onClick={showMoreDonationList}>더보기</ShowMoreButton>}
        </>
      ) : (
        <EmptyCommentsList>아직 후원한 사람이 없습니다.</EmptyCommentsList>
      )}
    </DonationListContainer>
  );
};

export default DonationMessageList;
