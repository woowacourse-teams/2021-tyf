import styled from 'styled-components';
import SubTitle from '../../@atom/SubTitle/SubTitle.styles';
import Title from '../../@atom/Title/Title.styles';

export const StyledCreatorGuideContent = styled.div`
  width: 100%;
`;

export const StyledContentContainer = styled.div`
  margin-bottom: 5rem;
`;

export const StyledTitle = styled(Title)`
  padding: 0;
  text-align: left;
  margin-bottom: 5rem;
`;

export const StyledSubTitle = styled(SubTitle)`
  text-align: left;
  margin-bottom: 3rem;
`;

export const StyledContents = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2.5rem;
  padding-left: 3rem;
`;

export const StyledImage = styled.div`
  width: 50%;

  img {
    width: 100%;
    object-fit: contain;
  }
`;

export const StyledContent = styled.div`
  width: 100%;
  padding-left: 3rem;

  p {
    width: 100%;
    line-height: 1.5rem;
    margin-bottom: 0.5rem;
  }
`;
