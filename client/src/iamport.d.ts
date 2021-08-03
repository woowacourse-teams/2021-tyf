export interface IamportResponse {
  success: boolean; // 결제처리가 성공적이었는지 여부	실제 결제승인이 이뤄졌거나, 가상계좌 발급이 성공된 경우, True
  error_code: string; // 결제처리에 실패한 경우 단축메세지	현재 코드체계는 없음
  error_msg: string; // 결제처리에 실패한 경우 상세메세지
  imp_uid: string; // 아임포트 거래 고유 번호	아임포트에서 부여하는 거래건 당 고유한 번호(success:false일 때, 사전 validation에 실패한 경우라면 imp_uid는 null일 수 있음)
  merchant_uid: string; // 가맹점에서 생성/관리하는 고유 주문번호
  pay_method: string; // 결제상태, 결제수단	card(신용카드), trans(실시간계좌이체), vbank(가상계좌), phone(휴대폰소액결제), kakaopay (이니시스, KCP, 나이스페이먼츠를 통한 카카오페이 직접 호출), payco (이니시스, KCP를 통한 페이코 직접 호출), lpay (이니시스를 통한 LPAY 직접 호출), ssgpay (이니시스를 통한 SSG페이 직접 호출), tosspay (이니시스를 통한 토스간편결제 직접 호출), point (카카오페이, PAYCO, 이니시스, 나이스페이먼츠 내 간편결제 시 해당 간편결제 자체 포인트 100% 결제)
  paid_amount: number; //결제금액	실제 결제승인된 금액이나 가상계좌 입금예정 금액
  status: string; // ready(미결제), paid(결제완료), cancelled(결제취소, 부분취소포함), failed(결제실패)
  name: string; // 주문명
  pg_provider: string; // 결제승인/시도된 PG사	html5_inicis(웹표준방식의 KG이니시스), inicis(일반 KG이니시스), kakaopay(카카오페이), uplus(LGU+), nice(나이스정보통신), jtnet(JTNet), danal(다날)
  pg_tid: string; // PG사 거래고유번호
  buyer_name: string; // 주문자 이름
  buyer_email: string; // 주문자 Email
  buyer_tel: string; // 주문자 연락처
  buyer_addr: string; // 주문자 주소
  buyer_postcode: string; // 주문자 우편번호
  custom_data: any; // 가맹점 임의 지정 데이터
  paid_at: number; // 결제승인시각 UNIX timestamp
  receipt_url: string;
}

export default interface Iamport {
  init: (accountID: string) => void;
  request_pay: (params: any, callback?: any) => void;
}

declare global {
  interface Window {
    IMP: Iamport;
  }
}
