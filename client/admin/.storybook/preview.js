import { RecoilRoot } from 'recoil';
import { BrowserRouter } from 'react-router-dom';

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
        <BrowserRouter>
          <Story />
        </BrowserRouter>
      </RecoilRoot>
    </>
  ),
];
