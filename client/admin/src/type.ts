export interface Exchange {
  exchangeAmount: number;
  accountNumber: string;
  nickname: string;
  pageName: string;
  createdAt: string;
}

export interface ExchangeListResponse {
  data?: Exchange[];
  errors?: Array<{ message: string }>;
}

export interface LoginResponse {
  data?: {
    token: string;
  };
  errors?: Array<{ message: string }>;
}
