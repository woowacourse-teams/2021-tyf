import whiteBackground from '../assets/images/dummy/profile-img.jpg';
import { Creator, LoginUserInfo, Statistics } from '../types';

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
