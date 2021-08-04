export enum AUTH_ERROR {
  ALREADY_REGISTER = 'auth-004',
  NOT_USER = 'auth-006',
}

export const AUTH_ERROR_MESSAGE = {
  [AUTH_ERROR.ALREADY_REGISTER]: '이미 가입되어 있는 사용자입니다.',
  [AUTH_ERROR.NOT_USER]: '유저가 아닙니다.',
} as const;

// AUTH_ERROR_MESSAGE[errorCode]
