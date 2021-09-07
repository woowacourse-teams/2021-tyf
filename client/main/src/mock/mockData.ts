import whiteBackground from '../assets/images/dummy/profile-img.jpg';
import { Creator, Donation, UserInfo } from '../types';

export const creatorListMock: Creator[] = [
  {
    nickname: 'asdf',
    profileImage: whiteBackground,
    bio: '빈칸아님',
    pageName: 'pageName',
    email: 'jho2301@gmail.com',
  },
  {
    nickname: 'asdf',
    profileImage: whiteBackground,
    bio: '',
    pageName: 'pageName',
    email: 'jho2301@gmail.com',
  },
  {
    nickname: 'asdf',
    profileImage: whiteBackground,
    bio: '안녕하세요~',
    pageName: 'pageName',
    email: 'jho2301@gmail.com',
  },
  {
    nickname: 'asdf',
    profileImage: whiteBackground,
    bio: '',
    pageName: 'pageName',
    email: 'jho2301@gmail.com',
  },
];

export const userInfoMock: UserInfo = {
  nickname: 'inch',
  bio: 'hi',
  profileImage: 'asdf',
  pageName: 'inchpage',
  email: 'jho2301@gmail.com',
  point: 1234,
};

export const donationMessageListMock: Donation[] = [
  {
    donationId: 1,
    name: '인치',
    message: '화이팅',
    donatedPoint: 10000,
    createdAt: new Date(),
    email: 'jhoasdf@naver.com',
  },
  {
    donationId: 2,
    name: '파노',
    message: '이것은',
    donatedPoint: 38810,
    createdAt: new Date(),
    email: 'jhoasdf@naver.com',
  },
  {
    donationId: 3,
    name: '수리',
    message: '목데이터입니다',
    donatedPoint: 1000,
    createdAt: new Date(),
    email: 'jhoasdf@naver.com',
  },
  {
    donationId: 4,
    name: '파즈',
    message: '와 즐겁다아아',
    donatedPoint: 100300,
    createdAt: new Date(),
    email: 'jhoasdf@naver.com',
  },
  {
    donationId: 5,
    name: '조이',
    message: '테스트신나',
    donatedPoint: 19900,
    createdAt: new Date(),
    email: 'jhoasdf@naver.com',
  },
  {
    donationId: 6,
    name: '로키',
    message: '살려주어어어',
    donatedPoint: 200000,
    createdAt: new Date(),
    email: 'jhoasdf@naver.com',
  },
  {
    donationId: 7,
    name: '브랜',
    message: '와와아아',
    donatedPoint: 1020,
    createdAt: new Date(),
    email: 'jhoasdf@naver.com',
  },
];

export const refundInfoMock = {
  chargedPoint: 5500,
  price: 5000,
  createdAt: '2021.08.03_17:24',
};

export const refundReadyMock = {
  email: 't**t@test.com',
  timeout: 300, // 5분
  resendCoolTime: 60, // 1분
};

export const refundVerificationMock = {
  refundAccessToken: 'refundAccessToken',
};
