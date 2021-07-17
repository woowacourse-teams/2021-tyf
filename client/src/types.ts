import { OAUTH } from './constants/oauth';

export type CreatorId = string;

export interface Creator {
  pageName: CreatorId;
  nickname: string;
  profileImgSrc: string;
  introduce: string;
  email: string;
}

export interface Register {
  email: string;
  nickName: string;
  oauthType: string;
  urlName: CreatorId;
}

export interface LoginUserInfo extends Creator {}

export type OAuthProvider = keyof typeof OAUTH;

export interface Statistics {
  point: number;
}

export type StorageType = 'LOCAL' | 'SESSION';
