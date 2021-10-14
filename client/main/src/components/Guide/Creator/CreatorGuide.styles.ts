import styled from 'styled-components';
import { DEVICE } from '../../../constants/device';
import PALETTE from '../../../constants/palette';
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
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 5rem;

  @media ${DEVICE.DESKTOP_LARGE} {
    flex-direction: row;
    padding-left: 3rem;
    margin-bottom: 2.5rem;
  }
`;

export const StyledImage = styled.div`
  width: 50%;
  background-color: ${PALETTE.GRAY_200};
  margin-bottom: 1.5rem;

  img {
    width: 100%;
    object-fit: contain;
  }

  @media ${DEVICE.DESKTOP_LARGE} {
    margin-bottom: 0;
  }
`;

export const StyledContent = styled.div`
  width: 100%;
  text-align: center;

  p {
    width: 100%;
    line-height: 1.5rem;
    margin-bottom: 0.5rem;
  }

  @media ${DEVICE.DESKTOP_LARGE} {
    padding-left: 3rem;
  }
`;
