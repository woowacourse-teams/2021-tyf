import styled from 'styled-components';
import SubTitle from '../../@atom/SubTitle/SubTitle.styles';
import Title from '../../@atom/Title/Title.styles';

export const StyledCreatorGuideContent = styled.div`
  width: 100%;
`;

export const StyledContentContainer = styled.div``;

export const StyledTitle = styled(Title)`
  padding: 0;
  text-align: left;
  margin-bottom: 2.5rem;
`;

export const StyledSubTitle = styled(SubTitle)`
  text-align: left;
  margin-bottom: 1.5rem;
`;

export const StyledContents = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2.5rem;
`;

export const StyledImage = styled.div`
  width: 50%;

  img {
    width: 100%;
    object-fit: contain;
  }
`;

export const StyledContent = styled.div``;
