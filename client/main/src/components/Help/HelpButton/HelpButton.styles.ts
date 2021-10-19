import styled from 'styled-components';

import { DEVICE } from '../../../constants/device';
import PALETTE from '../../../constants/palette';
import { Z_INDEX } from '../../../constants/style';

export const StyledHelpButton = styled.a`
  position: sticky;
  display: block;
  width: 3rem;
  height: 3rem;
  margin-left: auto;
  margin-right: 2rem;
  z-index: ${Z_INDEX.FOREGROUND};

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
    color: ${PALETTE.GRAY_400};
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
