import { ThemeProvider } from 'styled-components';
import GlobalStyle from '../src/Global.styles';
import { theme } from '../src/theme';

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
      <ThemeProvider theme={theme}>
        <GlobalStyle />
        <div style={{ height: '100vh' }}>
          <Story />
        </div>
      </ThemeProvider>
    </>
  ),
];
