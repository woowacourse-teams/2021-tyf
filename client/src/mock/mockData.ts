import whiteBackground from '../assets/images/dummy/profile-img.jpg';
import { Creator, Donation, LoginUserInfo, Statistics } from '../types';

export const creatorListMock: Creator[] = [
  {
    nickname: 'asdf',
    profileImgSrc: whiteBackground,
    introduce: '빈칸아님',
    pageName: 'pageName',
    email: 'jho2301@gmail.com',
  },
  {
    nickname: 'asdf',
    profileImgSrc: whiteBackground,
    introduce: '',
    pageName: 'pageName',
    email: 'jho2301@gmail.com',
  },
  {
    nickname: 'asdf',
    profileImgSrc: whiteBackground,
    introduce: '안녕하세요~',
    pageName: 'pageName',
    email: 'jho2301@gmail.com',
  },
  {
    nickname: 'asdf',
    profileImgSrc: whiteBackground,
    introduce: '',
    pageName: 'pageName',
    email: 'jho2301@gmail.com',
  },
];

export const userInfoMock: LoginUserInfo = {
  nickname: 'inch',
  introduce: 'hi',
  pageName: 'inchpage',
  profileImgSrc: 'asdf',
  email: 'jho2301@gmail.com',
};

export const statisticsMock: Statistics = {
  point: 200000,
};

export const donationMessageListMock: Donation[] = [
  { donationId: 1, name: '인치', message: '화이팅', amount: 10000, createdAt: new Date() },
  { donationId: 2, name: '파노', message: '이것은', amount: 38810, createdAt: new Date() },
  { donationId: 3, name: '수리', message: '목데이터입니다', amount: 1000, createdAt: new Date() },
  { donationId: 4, name: '파즈', message: '와 즐겁다아아', amount: 100300, createdAt: new Date() },
  { donationId: 5, name: '조이', message: '테스트신나', amount: 19900, createdAt: new Date() },
  { donationId: 6, name: '로키', message: '살려주어어어', amount: 200000, createdAt: new Date() },
  { donationId: 7, name: '브랜', message: '와와아아', amount: 1020, createdAt: new Date() },
];
