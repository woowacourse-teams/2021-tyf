import styled from 'styled-components';

import Container from '../../@atom/Container/Container';
import Title from '../../@atom/SubTitle/SubTitle';
import OutlineButton from '../../@molecule/OutlineButton/OutlineButton';

export const DonationListContainer = styled(Container)`
  margin-bottom: 4rem;
`;

export const DonationListTitle = styled(Title)`
  margin-bottom: 4rem;
`;

export const CommentsList = styled.ul`
  width: 100%;
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
  justify-content: center;
  padding: 0.625rem 0;
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
