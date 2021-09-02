import { BrowserRouter } from 'react-router-dom';
import { RecoilRoot } from 'recoil';
import { render } from '@testing-library/react';

export const myRender = (children: React.ReactNode) =>
  render(
    <RecoilRoot>
      <BrowserRouter>{children}</BrowserRouter>
    </RecoilRoot>
  );

export const mockAlert = () => {
  const alertMock = jest.fn();

  window.alert = alertMock;

  return alertMock;
};
