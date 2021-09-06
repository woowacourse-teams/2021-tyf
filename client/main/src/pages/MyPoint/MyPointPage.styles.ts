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

  @media ${DEVICE.DESKTOP_LARGE} {
    justify-content: center;
  }
`;

export const StyledContainer = styled(Container)`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 4.625rem 0;

  @media ${DEVICE.DESKTOP_LARGE} {
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

export const ChargeButton = styled(Button)`
  position: absolute;
  bottom: 6rem;

  @media ${DEVICE.DESKTOP_LARGE} {
    position: static;
    margin-top: 8rem;
  }
`;

export const Point = styled.span`
  font-weight: 600;
  margin-right: 0.25rem;
  font-size: 3rem;
`;
