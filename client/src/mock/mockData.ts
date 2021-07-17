import whiteBackground from '../assets/images/dummy/profile-img.jpg';
import { Creator, LoginUserInfo, Statistics } from '../types';

export const creatorListMock: Creator[] = [
  { name: 'asdf', profileImgSrc: whiteBackground, introduce: '빈칸아님', pageName: 'pageName' },
  { name: 'asdf', profileImgSrc: whiteBackground, introduce: '', pageName: 'pageName' },
  { name: 'asdf', profileImgSrc: whiteBackground, introduce: '안녕하세요~', pageName: 'pageName' },
  { name: 'asdf', profileImgSrc: whiteBackground, introduce: '', pageName: 'pageName' },
];

export const userInfoMock: LoginUserInfo = {
  name: 'inch',
  introduce: 'hi',
  pageName: 'inchpage',
  profileImgSrc: 'asdf',
};

export const statisticsMock: Statistics = {
  point: 200000,
};
