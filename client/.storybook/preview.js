import { RecoilRoot } from 'recoil';
import { ThemeProvider } from 'styled-components';
import { BrowserRouter } from 'react-router-dom';

import GlobalStyle from '../src/Global.styles';
import { theme } from '../src/theme';
import { Suspense } from 'react';

export const parameters = {
  actions: { argTypesRegex: '^on[A-Z].*' },
  controls: {
    matchers: {
      color: /(background|color)$/i,
      date: /Date$/,
    },
  },
};

export const decorators = [
  (Story) => (
    <>
      <RecoilRoot>
        <ThemeProvider theme={theme}>
          <GlobalStyle />
          <BrowserRouter>
            <div style={{ height: '100vh' }}>
              <Suspense fallback={true}>
                <Story />
              </Suspense>
            </div>
          </BrowserRouter>
        </ThemeProvider>
      </RecoilRoot>
    </>
  ),
];
