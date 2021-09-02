export interface Exchange {
  name : string,
	email :string,
  exchangeAmount: number;
  accountNumber: string;
  nickname: string;
  pageName: string;
  createdAt: string;
}

export interface LoginResponse {
  token: string;
}

export interface BankAccount {
  memberId: number;
  email: string;
  nickname: string;
  pageName: string;
  accountHolder: string;
  accountNumber: string;
  bank: string;
  bankbookImageUrl: string;
}
