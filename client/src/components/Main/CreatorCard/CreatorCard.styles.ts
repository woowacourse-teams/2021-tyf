import styled from 'styled-components';
import { DEVICE } from '../../../constants/device';

import PALETTE from '../../../constants/palette';
import { Z_INDEX } from '../../../constants/style';

interface CardProps {
  color: string;
}

export const StyledCreatorCard = styled.article<CardProps>`
  position: relative;
  width: 8.125rem;
  height: 9.5rem;
  border-radius: 5px;
  box-shadow: 2px 2px 1px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  background-color: ${({ color }) => color};

  ::before {
    content: '';
    width: 100%;
    position: absolute;
    bottom: 0;
    background-color: ${PALETTE.WHITE_400};
    z-index: ${Z_INDEX.BACKGROUND};
    height: 6rem;
  }

  @media ${DEVICE.DESKTOP_LARGE} {
    width: 13rem;
    height: 15.5rem;

    ::before {
      height: 9.25rem;
    }
  }
`;

export const ProfileImg = styled.img`
  position: absolute;
  width: 4rem;
  height: 4rem;
  object-fit: cover;
  border-radius: 50%;
  left: 50%;
  transform: translateX(-50%);
  z-index: ${Z_INDEX.MIDGROUND};
  top: 1.5rem;

  @media ${DEVICE.DESKTOP_LARGE} {
    width: 5.625rem;
    height: 5.625rem;
    top: 3.5rem;
  }
`;

export const Name = styled.h4`
  position: absolute;
  text-align: center;
  line-height: 1.625rem;
  font-weight: 600;
  top: 6.5rem;
  left: 50%;
  transform: translateX(-50%);

  @media ${DEVICE.DESKTOP_LARGE} {
    top: 10rem;
  }
`;

export const Bio = styled.p`
  width: 100%;
  padding: 0.75rem;
  padding-top: 0;
  text-align: center;
  position: absolute;
  top: 12rem;
  left: 50%;
  font-size: 0.875rem;
  color: ${PALETTE.GRAY_500};
  font-weight: 400;
  transform: translateX(-50%);
  line-height: 1rem;
  height: 3rem;
  overflow: hidden;
  display: -webkit-box;
  line-clamp: 3;
  box-orient: vertical;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
`;
