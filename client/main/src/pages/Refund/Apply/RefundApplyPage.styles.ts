import styled from 'styled-components';

import Template from '../../../components/@atom/Template/Template';
import { SIZE } from '../../../constants/device';

export const RefundApplyPageTemplate = styled(Template)`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  max-width: ${SIZE.MOBILE_MAX}px;
`;
