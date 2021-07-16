import { OAuth } from './constants/oauth';

export interface Creator {
  pageName: string;
  name: string;
  profileImgSrc: string;
  introduce: string;
}
export interface Register {
  email: string;
  nickName: string;
  oauthType: string;
  urlName: string;
}

export interface LoginUserInfo extends Creator {}

export type OAuthProvider = keyof typeof OAuth;

export type StorageType = 'LOCAL' | 'SESSION';
