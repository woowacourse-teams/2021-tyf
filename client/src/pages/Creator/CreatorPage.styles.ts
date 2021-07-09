import styled from 'styled-components';
import Container from '../../components/@atom/Container/Container';
import Title from '../../components/@atom/SubTitle/SubTitle';
import OutlineButton from '../../components/@molecule/OutlineButton/OutlineButton';
import PALETTE from '../../constants/palette';

export const CreatorPageContainer = styled(Container)`
  height: 100vh;
`;

export const ProfileContainer = styled(Container)`
  margin-bottom: 8rem;
`;

export const DescriptionContainer = styled(Container)`
  margin: 3rem 0 5rem 0;
  border-top: 1px solid ${({ theme }) => theme.color.border};
  border-bottom: 1px solid ${({ theme }) => theme.color.border};
  padding: 1rem;
  color: ${PALETTE.GRAY_500};
`;

export const CommentsContainer = styled(Container)`
  margin-bottom: 4rem;
`;

// TODO: 아래 버튼을 일반화해도 좋을듯
export const ShowMoreButton = styled(OutlineButton)`
  width: 7.75rem;
  height: 2.25rem;
  margin: 0 auto;
  display: block;
  margin-top: 1.75rem;
  font-size: 0.875rem;
  font-weight: 600;
`;

export const CommentsTitle = styled(Title)`
  margin-bottom: 2.5rem;
`;

export const CommentsList = styled.ul`
  width: 100%;
`;

export const CommentsListItem = styled.li`
  padding: 1rem 0;
  border-bottom: 1px solid ${({ theme }) => theme.color.border};
`;

export const ItemInfo = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
  font-weight: 700;
  margin-bottom: 1.5rem;
`;

export const ItemContent = styled.div`
  display: flex;
  justify-content: center;
  margin-bottom: 0.5rem;
`;

export const Divider = styled.span`
  padding: 0 0.5rem;
  color: ${({ theme }) => theme.color.border};
`;
