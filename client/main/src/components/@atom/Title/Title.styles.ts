import styled from 'styled-components';

import { DEVICE } from '../../../constants/device';

const Title = styled.h2`
  width: 100%;
  line-height: 2.5rem;
  padding: 0 1rem;
  text-align: center;
  font-size: 2rem;
  font-weight: 700;

  @media ${DEVICE.DESKTOP_LARGE} {
    font-size: 2.25rem;
  }
`;

export default Title;
