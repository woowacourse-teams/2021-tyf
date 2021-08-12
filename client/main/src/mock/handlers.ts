import { rest } from 'msw';
import { baseURL } from '../API';

import {
  creatorListMock,
  donationMessageListMock,
  statisticsMock,
  userInfoMock,
  refundInfoMock,
  refundReadyMock,
  refundVerificationMock,
} from './mockData';

const donationHandlers = [
  rest.get(`${baseURL}/donations/public/:pageName`, (req, res, ctx) => {
    return res(ctx.json(donationMessageListMock));
  }),

  rest.get(`${baseURL}/donations/me`, (req, res, ctx) => {
    const page = Number(req.url.searchParams.get('page'));
    const size = Number(req.url.searchParams.get('size'));
    return res(ctx.json(donationMessageListMock.slice(page * size, page * size + size)));
  }),

  rest.post(`${baseURL}/donations/:pageName/messages`, (req, res, ctx) => {
    return res(ctx.status(200));
  }),
];

const memberHandlers = [
  rest.get(`${baseURL}/members/me/point`, (req, res, ctx) => {
    return res(ctx.json(statisticsMock));
  }),
  rest.get(`${baseURL}/members/me`, (req, res, ctx) => {
    return res(ctx.json(userInfoMock));
  }),
  rest.get(`${baseURL}/members/curations`, (req, res, ctx) => {
    return res(ctx.json(creatorListMock));
  }),
  rest.get(`${baseURL}/members/:pageName`, (req, res, ctx) => {
    return res(ctx.json(userInfoMock));
  }),
];

const refundHandlers = [
  rest.post(`${baseURL}/payments/refund/verification/ready`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(refundReadyMock));
  }),

  rest.get(`${baseURL}/payments/refund/info`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(refundInfoMock));
  }),

  rest.post(`${baseURL}/payments/refund/verification`, (req, res, ctx) => {
    return res(ctx.json(refundVerificationMock));
  }),

  rest.post(`${baseURL}/payments/refund`, (req, res, ctx) => {
    return res(ctx.status(200));
  }),
];

const handlers = [...memberHandlers, ...donationHandlers, ...refundHandlers];

export default handlers;
