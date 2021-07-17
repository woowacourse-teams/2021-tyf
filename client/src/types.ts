import { OAUTH } from './constants/oauth';

export interface Creator {
  pageName: string;
  name: string;
  profileImgSrc: string;
  introduce: string;
}

export interface LoginUserInfo extends Creator {}

export type OAuthProvider = keyof typeof OAUTH;


export interface Statistics {
  point: number;
}

export type StorageType = 'LOCAL' | 'SESSION';
