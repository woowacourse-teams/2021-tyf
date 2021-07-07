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
    color: ${({ theme }) => theme.color.main}
  }

`;

export default GlobalStyle;
