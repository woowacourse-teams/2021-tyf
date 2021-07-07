import { createGlobalStyle } from 'styled-components';
import reset from 'styled-reset';

const GlobalStyle = createGlobalStyle`
  ${reset}

  * {
    box-sizing: border-box;
    outline: none;
  }



  :root {
    font-size: 16px;
    color: ${({ theme }) => theme.color.main};
    font-family: 'Noto Sans KR', sans-serif;
  }

  button {
    font-family: 'Noto Sans KR', sans-serif;
  }

`;

export default GlobalStyle;
