import styled from 'styled-components';

import { DEVICE } from '../../../constants/device';

export const StyledSubTitle = styled.h3`
  font-size: 1.5rem;
  font-weight: 600;
  text-align: center;

  @media ${DEVICE.DESKTOP_LARGE} {
    font-size: 2.25rem;
  }
`;
