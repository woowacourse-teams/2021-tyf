import styled from 'styled-components';

import { MIN_WIDTH, MAX_WIDTH } from './../../../constants/style';

export const StyledTemplate = styled.main`
  width: 100vw;
  min-height: 100vh; // TODO: 나중에 header 높이만큼 빼주세요
`;

export const InnerTemplate = styled.section`
  min-width: ${MIN_WIDTH};
  width: 100%;
  max-width: ${MAX_WIDTH};
  margin: 0 auto;
`;
