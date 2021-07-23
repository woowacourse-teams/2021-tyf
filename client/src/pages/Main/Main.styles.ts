import styled from 'styled-components';

import MainImage from '../../assets/images/hero-content.svg';
import SubTitle from '../../components/@atom/SubTitle/SubTitle';
import Template from '../../components/@atom/Template/Template';
import Logo from '../../components/@molecule/Logo/Logo';
import OutlineButton from '../../components/@molecule/OutlineButton/OutlineButton';
import { DEVICE } from '../../constants/device';

export const MainTemplate = styled(Template)`
  padding: 5rem 0 6.25rem;

  section {
    margin-bottom: 9rem;
  }
`;

export const HeroContent = styled.img.attrs({
  src: MainImage,
  draggable: false,
})`
  width: 100%;
  padding-left: 5%;
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
  margin-bottom: 3rem;

  ${Logo} {
    width: 14rem;
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
      width: 23.875rem;
    }

    br {
      display: none;
    }
  }
`;
