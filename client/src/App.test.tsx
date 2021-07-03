import { render, screen } from '@testing-library/react';

import App from './App';

describe('임시', () => {
  test('App 컴포넌트 렌더링 테스트', () => {
    render(<App hi="ho" />);

    // const title = screen.getByRole('h1');
  });
});
