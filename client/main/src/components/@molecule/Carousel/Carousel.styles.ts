import styled, { css } from 'styled-components';
import IconButton from '../../@atom/IconButton/IconButton';
import LeftArrow from '../../../assets/icons/left-arrow.svg';
import RightArrow from '../../../assets/icons/right-arrow.svg';

export const StyledCarousel = styled.div`
  margin: 0 3rem;
  position: relative;
  width: fit-content;
`;

interface ContentContainerProps {
  contentWidth?: number;
}

export const ContentContainer = styled.div<ContentContainerProps>`
  width: ${({ contentWidth }) => contentWidth ?? '100%'};
  display: flex;
  overflow: hidden;
`;

const ArrowStyle = css`
  position: absolute;
  bottom: 2.5rem;
  width: 1.5rem;
  height: 1.5rem;

  &:hover {
    opacity: 0.5;
  }
`;

export const LeftArrowButton = styled(IconButton).attrs({ src: LeftArrow })`
  ${ArrowStyle}
  left: -3rem;
`;

export const RightArrowButton = styled(IconButton).attrs({ src: RightArrow })`
  ${ArrowStyle}
  right: -3rem;
`;
