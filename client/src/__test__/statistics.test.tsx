import { screen } from '@testing-library/react';
import { statisticsMock } from '../mock/mockData';
import StatisticsPage from '../pages/Statistics/StatisticsPage';
import { myRender } from './utils/testUtil';

beforeEach(() => {
  myRender(<StatisticsPage />);
});

describe('statistics Page', () => {
  test('창작자의 후원 총 금액을 나타낸다.', async () => {
    const $totalAmount = await screen.findByRole('total-amount', { hidden: true });

    expect($totalAmount.textContent).toBe(statisticsMock.point.toLocaleString('en-us'));
  });
});
