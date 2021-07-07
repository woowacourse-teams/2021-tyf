import { ThemeType } from './theme';
import { createGlobalStyle } from 'styled-components';
import reset from 'styled-reset';

const GlobalStyle = createGlobalStyle<{ theme: ThemeType }>`
  ${reset}

  * {
    box-sizing: border-box;
    outline: none;
  }


  :root {
    font-size: 16px;
    color: ${(props) => props.theme.color.main}
  }

`;

export default GlobalStyle;
