import { OAUTH } from './constants/oauth';

// OAUTH

export type OAuthProvider = keyof typeof OAUTH;

// STORAGE

export type StorageType = 'LOCAL' | 'SESSION';

// CREATOR

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

// STATISTICS
export interface Statistics {
  point: number;
}

// DONATION

export type DonationId = number;
export interface Donation {
  donationId: DonationId;
  name: string;
  message: string;
  amount: number;
  createdAt: Date;
}

export type DonationMessage = Pick<Donation, 'name' | 'message'>;
