import styled, { keyframes } from 'styled-components';

import SubTitle from '../../components/@atom/SubTitle/SubTitle.styles';
import Template from '../../components/@atom/Template/Template';
import Logo from '../../components/@molecule/Logo/Logo';
import OutlineButton from '../../components/@molecule/OutlineButton/OutlineButton.styles';
import { DEVICE } from '../../constants/device';

import RightArrow from '../../assets/icons/right-arrow.svg';

export const MainTemplate = styled(Template)`
  padding: 5rem 0 6.25rem;

  section {
    margin-bottom: 9rem;
  }

  @media ${DEVICE.DESKTOP_LARGE} {
    section:nth-of-type(1) {
      margin-top: 3rem;
    }

    section {
      margin-bottom: 12rem;
      display: flex;
      justify-content: center;
      align-items: center;
      flex-direction: column;
    }
  }
`;

export const HeroContent = styled.picture.attrs({
  draggable: false,
})`
  width: 100%;

  img {
    padding-left: 5%;
    width: 100%;
  }

  @media ${DEVICE.DESKTOP} {
    width: 90%;
    padding-left: 0;
  }
`;

export const RouteButton = styled(OutlineButton)`
  width: 7.75rem;
  height: 2.25rem;
  margin: 0 auto;
  display: block;
  margin-top: 1.75rem;
  font-size: 0.875rem;
  font-weight: 600;
`;

export const StyledSubTitle = styled(SubTitle)`
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 0.75rem;
  line-height: 1.75rem;

  ${Logo} {
    width: 14rem;
  }

  @media ${DEVICE.DESKTOP_LARGE} {
    margin-bottom: 1.5rem;
  }
`;

export const DescriptionContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  ${Logo} {
    width: 12rem;
  }

  @media ${DEVICE.DESKTOP_LARGE} {
    ${Logo} {
      width: 17rem;
    }

    br {
      display: none;
    }
  }
`;

const downKeyFrames = keyframes`
0% {
  bottom: 7%;
  opacity: 0;
}

100%{
  bottom: 5%;
  opacity: 1;
}
`;

export const DownIcon = styled.img.attrs({ src: RightArrow })`
  transform: rotate(90deg);
  width: 1.5rem;
  height: 1.5rem;
  position: fixed;
  bottom: 5%;
  left: 50%;

  animation: 1.2s infinite ${downKeyFrames};
`;
