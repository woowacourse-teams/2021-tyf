export interface Exchange {
  exchangeAmount: number;
  accountNumber: string;
  nickname: string;
  pageName: string;
  createdAt: string;
}

export interface LoginResponse {
  token: string;
}
