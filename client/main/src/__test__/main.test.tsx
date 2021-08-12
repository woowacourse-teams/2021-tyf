import { screen } from '@testing-library/react';

import MainPage from '../pages/Main/MainPage';
import { myRender } from './utils/testUtil';

beforeEach(() => {
  myRender(<MainPage />);
});

describe('MainPage', () => {
  test('창작자 리스트를 조회한다.', async () => {
    Element.prototype.scroll = jest.fn();
    const [$creatorListCard] = await screen.findAllByRole('creator-card');
    expect($creatorListCard).toBeInTheDocument();
  });
});
