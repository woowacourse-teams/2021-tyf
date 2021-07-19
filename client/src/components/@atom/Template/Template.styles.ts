import styled from 'styled-components';

import { MIN_WIDTH, MAX_WIDTH } from './../../../constants/style';

export const StyledTemplate = styled.main`
  width: 100vw;
`;

export const InnerTemplate = styled.section`
  padding: 3rem 1rem;

  min-width: ${MIN_WIDTH};
  width: 100%;
  max-width: ${MAX_WIDTH};
  min-height: calc(100vh - 3.5rem);
  min-height: calc(${window.innerHeight}px - 3.5rem);
  margin: 0 auto;
`;
