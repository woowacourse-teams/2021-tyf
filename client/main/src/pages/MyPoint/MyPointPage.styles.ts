import styled from 'styled-components';

import Template from '../../components/@atom/Template/Template';
import Button from '../../components/@atom/Button/Button.styles';
import { DEVICE } from '../../constants/device';
import Container from '../../components/@atom/Container/Container.styles';
import PALETTE from '../../constants/palette';

export const StyledTemplate = styled(Template)`
  display: flex;
  flex-direction: column;
  align-items: center;

  @media ${DEVICE.DESKTOP} {
    justify-content: center;
  }
`;

export const StyledContainer = styled.div`
  position: relative;
  min-height: calc(100vh - 3.5rem);
  padding: 4.625rem 0;
  width: 100%;

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
    width: 22rem;
    border-top: 1px solid ${PALETTE.GRAY_300};
    border-bottom: 1px solid ${PALETTE.GRAY_300};
  }
`;

export const StyledTitle = styled.h1`
  font-size: 2rem;
  font-weight: 600;
  margin-bottom: 6rem;
  text-align: left;
  align-self: flex-start;
  line-height: 2.5rem;
`;

export const ChargeButton = styled(Button)``;

export const Point = styled.span`
  font-weight: 600;
  margin-right: 0.25rem;
  font-size: 3rem;
`;
