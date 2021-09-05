import { render } from '@testing-library/react';
import { Suspense } from 'react';
import { BrowserRouter } from 'react-router-dom';
import { RecoilRoot } from 'recoil';
import { ThemeProvider } from 'styled-components';
import Spinner from '../../components/Spinner/Spinner';

import { theme } from '../../theme';

export const myRender = (children: React.ReactNode, initializeState?: ({ set }: any) => void) =>
  render(
    <RecoilRoot initializeState={initializeState}>
      <ThemeProvider theme={theme}>
        <Suspense fallback={<Spinner />}>
          <BrowserRouter>{children}</BrowserRouter>
        </Suspense>
      </ThemeProvider>
    </RecoilRoot>
  );

export const mockHistoryPush = () => {
  const historyPushMock = jest.fn();

  jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),
    useHistory: () => ({
      push: historyPushMock,
    }),
  }));

  return historyPushMock;
};

export const mockAlert = () => {
  const alertMock = jest.fn();

  window.alert = alertMock;

  return alertMock;
};
