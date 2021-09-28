import styled, { keyframes } from 'styled-components';

import SubTitle from '../../components/@atom/SubTitle/SubTitle.styles';
import Template from '../../components/@atom/Template/Template';
import Logo from '../../components/@molecule/Logo/Logo';
import OutlineButton from '../../components/@molecule/OutlineButton/OutlineButton.styles';
import { DEVICE } from '../../constants/device';
import RightArrow from '../../assets/icons/right-arrow.svg';
import IconButton from '../../components/@atom/IconButton/IconButton';
import PALETTE from '../../constants/palette';
import { Z_INDEX } from '../../constants/style';
import { Link } from 'react-router-dom';

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
    height: auto;

    aspect-ratio: 1024 / 606;
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
  font-weight: 700;
`;

export const StyledSubTitle = styled(SubTitle)`
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 0.75rem;
  line-height: 1.75rem;

  ${Logo} {
    width: 14rem;
    aspect-ratio: 300 / 72;
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
    aspect-ratio: 300 / 72;
  }

  @media ${DEVICE.DESKTOP_LARGE} {
    ${Logo} {
      width: 17rem;
      aspect-ratio: 300 / 72;
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

export const DownIcon = styled.img.attrs({ src: RightArrow, alt: 'down icon' })`
  transform: rotate(90deg);
  width: 1.5rem;
  height: 1.5rem;
  position: fixed;
  bottom: 5%;
  left: 50%;

  animation: 1.2s infinite ${downKeyFrames};
`;

export const HelpButton = styled.a`
  position: sticky;
  display: block;
  width: 3rem;
  height: 3rem;
  margin-left: auto;
  margin-right: 2rem;

  bottom: 3rem;
  background-color: ${PALETTE.WHITE_400};
  border-radius: 100%;
  border: 3px solid ${PALETTE.GRAY_300};
  cursor: pointer;
  transition: transform 0.5s ease;

  ::after {
    content: '?';
    width: 100%;
    height: 100%;
    color: ${PALETTE.BLACK_400};
    font-weight: 700;
    font-size: 1.75rem;
    line-height: 2.5rem;
    position: absolute;
    text-align: center;
    vertical-align: middle;
  }

  :hover {
    transform: translateY(-0.375rem);
    background-color: ${PALETTE.GRAY_100};
  }

  :active {
    background-color: ${PALETTE.GRAY_200};
  }

  @media ${DEVICE.DESKTOP_LARGE} {
    position: fixed;
    right: 3rem;
  }
`;
