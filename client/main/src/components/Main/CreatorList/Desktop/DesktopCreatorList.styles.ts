import styled, { css } from 'styled-components';
import { DEVICE } from '../../../../constants/device';

import Container from '../../../@atom/Container/Container.styles';
import IconButton from '../../../@atom/IconButton/IconButton';

export const List = styled.ul`
  box-sizing: content-box;
  padding: 1.5rem;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;

  overflow-x: scroll;
  -ms-overflow-style: none; // ms
  scrollbar-width: none; // firefox

  &::-webkit-scrollbar {
    display: none;
  }

  & li {
    padding: 0 1rem;
  }
`;

export const ItemContainer = styled(Container)`
  flex-direction: row;
  justify-content: flex-start;
  overflow: hidden;
  padding: 0.5rem 0;
  max-width: 45rem;

  @media ${DEVICE.DESKTOP_EXTRA} {
    max-width: 60rem;
  }
`;

const ArrowStyle = css`
  position: absolute;
  width: 2rem;
  height: 2rem;

  &:hover {
    opacity: 0.5;
  }
`;

export const LeftArrowButton = styled(IconButton)`
  ${ArrowStyle}
  left: -0.25rem;
`;

export const RightArrowButton = styled(IconButton)`
  ${ArrowStyle}
  right: -0.25rem;
`;
