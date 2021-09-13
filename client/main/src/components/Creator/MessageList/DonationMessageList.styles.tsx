import styled from 'styled-components';

import Container from '../../@atom/Container/Container.styles';
import SubTitle from '../../@atom/SubTitle/SubTitle.styles';
import OutlineButton from '../../@molecule/OutlineButton/OutlineButton.styles';

export const DonationListContainer = styled(Container)`
  margin-bottom: 4rem;
`;

export const DonationListTitle = styled(SubTitle)`
  font-size: 1.5rem;
  margin-bottom: 4rem;
`;

export const CommentsList = styled.ul`
  width: 100%;
`;

export const EmptyCommentsList = styled.div`
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
`;

export const CommentsListItem = styled.li`
  padding: 1rem 0.5rem;
  border-bottom: 1px solid ${({ theme }) => theme.color.border};
`;

export const ItemInfo = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-weight: 700;
  font-size: 0.875rem;
  margin-bottom: 1.5rem;
`;

export const ItemContent = styled.div`
  display: flex;
  justify-content: flex-start;
  padding: 0.625rem;
  margin-bottom: 0.5rem;
`;

export const Divider = styled.span`
  padding: 0 0.5rem;
  color: ${({ theme }) => theme.color.border};
`;

export const ItemDateInfo = styled.span`
  font-size: 0.75rem;
  font-weight: 400;
`;

export const ShowMoreButton = styled(OutlineButton)`
  width: 7.75rem;
  height: 2.25rem;
  margin: 0 auto;
  display: block;
  margin-top: 1.75rem;
  font-size: 0.875rem;
  font-weight: 700;
`;
