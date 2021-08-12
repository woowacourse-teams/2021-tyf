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
  profileImage: string;
  bio: string;
  email: string;
}

export interface Register extends Pick<Creator, 'pageName' | 'nickname' | 'email'> {
  oauthType: string;
}

export interface UserInfo extends Creator {}

// STATISTICS

export interface Statistics {
  point: number;
}

// DONATION

export type DonationId = number;

export interface Donation {
  donationId: DonationId;
  email: string;
  name: string;
  message: string;
  amount: number;
  createdAt: Date;
}

export type DonationMessage = Pick<Donation, 'name' | 'message'>;

// PAYMENT

export interface Payment {
  amount: number;
  email: string;
  pageName: string;
}

// REFUND

export interface Refund {
  email: string;
  merchantUid: string;
  timeout: number;
  resendCoolTime: number;
  refundAccessToken: string;
}

export interface refundOrderDetail {
  creator: Pick<Creator, 'nickname' | 'pageName'>;
  donation: Pick<Donation, 'name' | 'amount' | 'message' | 'createdAt'>;
}

// SETTLEMENT

export type SettlementAccountStatus = 'REGISTERED' | 'UNREGISTERED' | 'REQUESTING' | 'REJECTED';

export interface SettlementAccount {
  status: SettlementAccountStatus;
  accountHolder: string;
  bank: string;
  accountNumber: string;
}

export interface SettlementAccountForm {
  accountHolder: string;
  bank: string | null;
  bankbookImage: File | null;
  accountNumber: string;
}

export interface Point {
  currentPoint: number;
  exchangeablePoint: number;
  exchangedTotalPoint: number;
}