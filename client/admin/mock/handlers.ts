import { rest } from 'msw';
import { BASE_URL } from '../src/constants/api';

import { loginMock, settlementListMock } from './mockData';

const loginHandlers = [
  rest.get(`${BASE_URL}/admin/login`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(loginMock));
  }),
];

const settlementHandlers = [
  rest.get(`${BASE_URL}/admin/list/exchange`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(settlementListMock));
  }),

  rest.get(`${BASE_URL}/admin/exchange/approve/pageName`, (req, res, ctx) => {
    return res(ctx.status(200));
  }),

  rest.get(`${BASE_URL}/admin/exchange/reject`, (req, res, ctx) => {
    return res(ctx.status(200));
  }),
];

export const handlers = [...loginHandlers, ...settlementHandlers];
