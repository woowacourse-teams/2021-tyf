import { createGlobalStyle } from 'styled-components';
import reset from 'styled-reset';
import PALETTE from './constants/palette';

const GlobalStyle = createGlobalStyle`
  ${reset}

  * {
    box-sizing: border-box;
    outline: none;
    padding: 0;
    margin:0;
  }

  *::selection {
    color: ${PALETTE.WHITE_400};
    background: ${({ theme }) => theme.primary.base};
  }

  a {
    text-decoration: none;

    &:active, &:hover {
      color: inherit;
    }
  }

  :root {
    font-size: 16px;
    color: ${({ theme }) => theme.color.main};
    font-family: 'Noto Sans KR', sans-serif;
    -webkit-tap-highlight-color:  rgba(255, 255, 255, 0);


    ::-webkit-scrollbar {
      display: none;
    };
  }

  button {
    font-family: 'Noto Sans KR', sans-serif;
  }
`;

export default GlobalStyle;
