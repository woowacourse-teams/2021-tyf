import ReactDOM from 'react-dom';
import { ThemeProvider } from 'styled-components';
import { BrowserRouter } from 'react-router-dom';

import App from './App';
import GlobalStyle from './Global.styles';
import { theme } from './theme';
import { RecoilRoot } from 'recoil';
import { Suspense } from 'react';
import { ErrorBoundary } from 'react-error-boundary';
import Spinner from './components/Spinner/Spinner';
import ErrorFallback from './components/ErrorFallback/ErrorFallback';

// TODO: APP감싸는 에러바운더리 화면 마크업 작업 필요

ReactDOM.render(
  <>
    <RecoilRoot>
      <ThemeProvider theme={theme}>
        <GlobalStyle />
        <BrowserRouter>
          <ErrorBoundary fallback={<ErrorFallback />}>
            <Suspense fallback={<Spinner />}>
              <App />
            </Suspense>
          </ErrorBoundary>
        </BrowserRouter>
      </ThemeProvider>
    </RecoilRoot>
  </>,
  document.getElementById('root')
);
