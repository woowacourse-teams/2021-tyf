import { css } from 'styled-components';

export const Z_INDEX = {
  FOREGROUND: 999,
  MIDGROUND: 99,
  BACKGROUND: 0,
};

export const MIN_PAGE_HEIGHT = 'calc(100vh - 4rem)';

export const flexCenterStyle = css`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
`;
