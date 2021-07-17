import { OAUTH } from './constants/oauth';

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

export type OAuthProvider = keyof typeof OAUTH;

export type StorageType = 'LOCAL' | 'SESSION';
