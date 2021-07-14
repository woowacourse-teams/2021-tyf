import ReactDOM from 'react-dom';
import { ThemeProvider } from 'styled-components';
import { BrowserRouter } from 'react-router-dom';

import App from './App';
import GlobalStyle from './Global.styles';
import { theme } from './theme';
import { RecoilRoot } from 'recoil';

ReactDOM.render(
  <>
    <RecoilRoot>
      <ThemeProvider theme={theme}>
        <GlobalStyle />
        <BrowserRouter>
          <App />
        </BrowserRouter>
      </ThemeProvider>
    </RecoilRoot>
  </>,
  document.getElementById('root')
);
