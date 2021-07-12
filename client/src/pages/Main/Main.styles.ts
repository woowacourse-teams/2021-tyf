import styled from 'styled-components';

import MainImage from '../../assets/images/HeroContent.svg';
import Template from '../../components/@atom/Template/Template';
import OutlineButton from '../../components/@molecule/OutlineButton/OutlineButton';

export const MainTemplate = styled(Template)`
  padding: 5rem 0 6.25rem;

  section:nth-of-type(1) {
    margin-bottom: 9rem;
  }

  section:nth-of-type(2) {
    margin-bottom: 8.25rem;
  }
`;

export const HeroContent = styled.img.attrs({
  src: MainImage,
})`
  width: 100%;
  padding-left: 1rem;
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
