import { FC, HTMLAttributes } from 'react';
import Button from '../../components/@atom/Button/Button';
import Template from '../../components/@atom/Template/Template';
import Profile from '../../components/Profile/Profile';
import {
  CreatorPageContainer,
  ProfileContainer,
  DescriptionContainer,
  ShowMoreButton,
  CommentsContainer,
  CommentsTitle,
  CommentsList,
  CommentsListItem,
  ItemInfo,
  Divider,
  ItemContent,
} from './CreatorPage.styles';

const CreatorPage: FC<HTMLAttributes<HTMLElement>> = () => {
  return (
    <Template>
      <CreatorPageContainer>
        <ProfileContainer>
          <Profile />
          <DescriptionContainer>제 페이지에 와주셔서 감사합니다!!</DescriptionContainer>
          <Button>내 페이지 공유하기</Button>
        </ProfileContainer>
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
      </CreatorPageContainer>
    </Template>
  );
};

export default CreatorPage;
