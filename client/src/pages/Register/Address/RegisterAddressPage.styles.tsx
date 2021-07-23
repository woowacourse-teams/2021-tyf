import styled from 'styled-components';
import Template from '../../../components/@atom/Template/Template';
import { SIZE } from '../../../constants/device';

export const StyledTemplate = styled(Template)`
  display: flex;
  max-width: ${SIZE.MOBILE_MAX}px;
`;
