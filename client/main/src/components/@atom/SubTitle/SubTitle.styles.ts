import styled from 'styled-components';

import { DEVICE } from '../../../constants/device';

const SubTitle = styled.h3`
  font-size: 1.25rem;
  font-weight: 700;
  text-align: center;

  @media ${DEVICE.DESKTOP_LARGE} {
    font-size: 1.75rem;
  }
`;

export default SubTitle;
