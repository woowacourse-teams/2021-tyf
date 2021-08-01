export enum AUTH_ERROR {
  NOT_USER = 'auth-006',
}

export const AUTH_ERROR_MESSAGE = {
  [AUTH_ERROR.NOT_USER]: '유저가 아닙니다.',
} as const;

// AUTH_ERROR_MESSAGE[errorCode]
