export enum REGISTER_ERROR {
  DUPLICATE_NICKNAME = 'member-005',
}

export const REGISTER_ERROR_MESSAGE = {
  [REGISTER_ERROR.DUPLICATE_NICKNAME]: '이미 존재하는 닉네임입니다. 다른 닉네임을 입력해주세요.',
} as const;

export enum AUTH_ERROR {
  INVALID_TOKEN = 'auth-002',
  ALREADY_REGISTER = 'auth-004',
  NOT_USER = 'auth-006',
}

export const AUTH_ERROR_MESSAGE = {
  [AUTH_ERROR.INVALID_TOKEN]: '유효하지 않은 토큰입니다.',
  [AUTH_ERROR.ALREADY_REGISTER]: '이미 가입되어 있는 사용자입니다.',
  [AUTH_ERROR.NOT_USER]: '유저가 아닙니다.',
} as const;

export enum PAYMENT_ERROR {
  EXCEED_TRY_COUNT = 'payment-012',
  NOT_READY = 'payment-011',
  PENDING = 'payment-013',
}

export const PAYMENT_ERROR_MESSAGE = {
  [PAYMENT_ERROR.EXCEED_TRY_COUNT]: '인증 횟수를 모두 소진하셨었습니다. 고객센터로 연락해주세요.',
  [PAYMENT_ERROR.PENDING]: '해당 결제는 환불 할 수 없는 상태입니다. 고객센터로 연락해주세요.',
  [PAYMENT_ERROR.NOT_READY]: '인증번호 재전송 대기시간입니다.',
};

export enum DONATION_ERROR {
  INVALID_USER = 'member-001',
  NOT_ENOUGH_POINT = 'donation-010',
}

export const DONATION_ERROR_MESSAGE = {
  [DONATION_ERROR.INVALID_USER]: '존재하지 않는 창작자 입니다.',
  [DONATION_ERROR.NOT_ENOUGH_POINT]: '보유 포인트가 충분하지 않습니다. 포인트를 충전하시겠습니까?',
} as const;

export enum CHARGE_ERROR {
  NOT_FINISHED_PAYMENT = 'payment-002',
  EMPTY_AUTH = 'auth-001',
  INVALID_AUTH = 'auth-002',
}

export const CHARGE_ERROR_MESSAGE = {
  [CHARGE_ERROR.NOT_FINISHED_PAYMENT]: '결제가 완료되지 않았습니다.',
  [CHARGE_ERROR.EMPTY_AUTH]: '로그인이 만료되었습니다. 다시 로그인해주세요.',
  [CHARGE_ERROR.INVALID_AUTH]: '로그인이 만료되었습니다. 다시 로그인해주세요.',
};

export enum REFUND_ERROR {
  INVALID_CHARGE = 'donation-003',
  ALREADY_CANCELLED = 'donation-006',
  ALREADY_REFUND = 'payment-013',
}

export const REFUND_ERROR_MESSAGE = {
  [REFUND_ERROR.INVALID_CHARGE]:
    '해당 결제 내역이 존재하지 않습니다. 문제가 지속되면 고객센터로 문의해주세요.',
  [REFUND_ERROR.ALREADY_CANCELLED]:
    '이미 취소신청이 완료된 결제 내역입니다. 문제가 지속되면 고객센터로 문의해주세요.',
  [REFUND_ERROR.ALREADY_REFUND]:
    '이미 환불 완료된 결제 내역입니다. 문제가 지속되면 고객센터로 문의해주세요.',
};

export enum SETTLEMENT_ERROR {
  ALREADY_SUBMITTED = 'exchange-001',
  UNDER_ABLE_POINT = 'exchange-002',
  EMPTY_AUTH = 'auth-001',
  INVALID_AUTH = 'auth-002',
  INVALID_USER = 'member-001',
}

export const SETTLEMENT_ERROR_MESSAGE = {
  [SETTLEMENT_ERROR.ALREADY_SUBMITTED]: '이미 정산신청이 완료되었습니다.',
  [SETTLEMENT_ERROR.UNDER_ABLE_POINT]: '10000 포인트 미만은 정산 신청을 할 수 없습니다.',
  [SETTLEMENT_ERROR.EMPTY_AUTH]: '로그인이 만료되었습니다. 다시 로그인해주세요.',
  [SETTLEMENT_ERROR.INVALID_AUTH]: '로그인이 만료되었습니다. 다시 로그인해주세요.',
  [SETTLEMENT_ERROR.INVALID_USER]: '로그인이 만료되었습니다. 다시 로그인해주세요.',
};

export enum SETTLEMENT_ACCOUNT_ERROR {
  ALREADY_APPLIED = 'member-007',
  ALREADY_SUBMITTED = 'member-008',
  INVALID_FORM = 'member-009',
  INVALID_ACCOUNT = 'member-010',
  INVALID_NAME = 'member-011',
  EMPTY_AUTH = 'auth-001',
  INVALID_AUTH = 'auth-002',
}

export const SETTLEMENT_ACCOUNT_ERROR_MESSAGE = {
  [SETTLEMENT_ACCOUNT_ERROR.ALREADY_APPLIED]: '이미 등록된 계좌입니다.',
  [SETTLEMENT_ACCOUNT_ERROR.ALREADY_SUBMITTED]: '이미 계좌등록 신청이 완료되었습니다.',
  [SETTLEMENT_ACCOUNT_ERROR.INVALID_FORM]:
    '작성한 계좌등록 정보가 잘못되었습니다. 다시 확인해주세요.',
  [SETTLEMENT_ACCOUNT_ERROR.EMPTY_AUTH]: '로그인이 만료되었습니다. 다시 로그인해주세요.',
  [SETTLEMENT_ACCOUNT_ERROR.INVALID_AUTH]:
    '계좌주 이름과 입력하신 이름이 일치하지 않습니다. 다시 한 번 확인해주세요',
  [SETTLEMENT_ACCOUNT_ERROR.INVALID_NAME]: '로그인이 만료되었습니다. 다시 로그인해주세요.',
  [SETTLEMENT_ACCOUNT_ERROR.INVALID_ACCOUNT]:
    '유효하지 않은 계좌입니다. 은행과 계좌 번호를 다시 확인 해주세요.',
};

export enum SETTING_ERROR {
  INVALID_NICKNAME = 'member-004',
  INVALID_BIO = 'member-006',
  EMPTY_AUTH = 'auth-001',
  INVALID_AUTH = 'auth-002',
  INVALID_USER = 'member-001',
}

export const SETTING_ERROR_MESSAGE = {
  [SETTING_ERROR.INVALID_NICKNAME]: '닉네임 형식이 올바르지 않습니다. 다시 확인해주세요.',
  [SETTING_ERROR.INVALID_BIO]: '자기소개 형식이 올바르지 않습니다. 다시 확인해주세요.',
  [SETTING_ERROR.EMPTY_AUTH]: '로그인이 만료되었습니다. 다시 로그인해주세요.',
  [SETTING_ERROR.INVALID_AUTH]: '로그인이 만료되었습니다. 다시 로그인해주세요.',
  [SETTING_ERROR.INVALID_USER]: '로그인이 만료되었습니다. 다시 로그인해주세요.',
};
