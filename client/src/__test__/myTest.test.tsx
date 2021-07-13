import { screen } from '@testing-library/react';
import MainPage from '../pages/Main/MainPage';
import { myRender } from './utils/testUtil';

describe('테스트', () => {
  test('테스트하자', () => {
    myRender(<MainPage />);

    const creatorListTitle = screen.getByRole('heading', {
      name: /thank you for ___의 창작자들/i,
    });

    expect(creatorListTitle).toBeInTheDocument();
  });
});
