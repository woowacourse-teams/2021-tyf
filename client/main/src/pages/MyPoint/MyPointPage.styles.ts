import styled from 'styled-components';

import Template from '../../components/@atom/Template/Template';
import Button from '../../components/@atom/Button/Button.styles';
import { DEVICE } from '../../constants/device';
import PALETTE from '../../constants/palette';

export const StyledTemplate = styled(Template)`
  display: flex;
  flex-direction: column;
  align-items: center;
  min-height: calc(100vh - 3.5rem);

  @media ${DEVICE.DESKTOP} {
    justify-content: center;
  }
`;

export const StyledContainer = styled.div`
  position: relative;
  padding: 4.625rem 0;
  width: 100%;
  min-height: calc(100vh - 3.5rem);

  section {
    width: 100%;
  }

  section:nth-of-type(2) {
    position: absolute;
    bottom: 6rem;

    @media ${DEVICE.DESKTOP} {
      position: static;
      margin-top: 8rem;
    }
  }

  @media ${DEVICE.DESKTOP} {
    min-height: 0;
    width: 22rem;
    border-top: 1px solid ${PALETTE.GRAY_300};
    border-bottom: 1px solid ${PALETTE.GRAY_300};
  }
`;

export const StyledTitle = styled.h1`
  font-size: 2rem;
  font-weight: 700;
  margin-bottom: 6rem;
  text-align: left;
  align-self: flex-start;
  line-height: 2.5rem;
`;

export const ChargeButton = styled(Button)``;

export const Point = styled.span`
  font-weight: 700;
  margin-right: 0.25rem;
  font-size: 3rem;
`;
