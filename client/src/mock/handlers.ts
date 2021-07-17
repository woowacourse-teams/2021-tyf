import { rest } from 'msw';
import { statisticsMock, userInfoMock } from './mockData';

export const handlers = [
  rest.get('/members/me/point', (req, res, ctx) => {
    console.log('/members/me/point request');
    return res(ctx.json(statisticsMock));
  }),
  rest.get('/members/me', (req, res, ctx) => {
    return res(ctx.json(userInfoMock));
  }),
];
