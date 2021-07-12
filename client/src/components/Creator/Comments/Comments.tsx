import { FC, HTMLAttributes } from 'react';

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
  return (
    <CommentsContainer>
      <CommentsTitle>파노님을 응원하는 사람들</CommentsTitle>
      <CommentsList>
        <CommentsListItem>
          <ItemInfo>
            <span>
              짭이유 <Divider>|</Divider> 1000원
            </span>
            <span>21.08.02</span>
          </ItemInfo>
          <ItemContent>파노님 ㅜㅜ 너무 팬이에요. 연재하시는 소설 잘보고 있어요!!!</ItemContent>
        </CommentsListItem>
        <CommentsListItem>
          <ItemInfo>
            <span>
              짭이유 <Divider>|</Divider> 1000원
            </span>
            <span>21.08.02</span>
          </ItemInfo>
          <ItemContent>파노님 ㅜㅜ 너무 팬이에요. 연재하시는 소설 잘보고 있어요!!!</ItemContent>
        </CommentsListItem>
      </CommentsList>
      <ShowMoreButton>더보기</ShowMoreButton>
    </CommentsContainer>
  );
};

export default Comments;
