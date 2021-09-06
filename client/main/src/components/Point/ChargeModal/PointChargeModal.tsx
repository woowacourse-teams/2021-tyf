import { ChangeEvent, useState } from 'react';

import KakaoPay from '../../../assets/icons/kakao-pay.svg';
import { IamportResponse, RequestPayParams } from '../../../iamport';
import { requestPayment, requestPaymentComplete } from '../../../service/@request/payments';
import { popupTerms } from '../../../service/@shared/popupTerms';
import useAccessToken from '../../../service/@shared/useAccessToken';
import { toCommaSeparatedString } from '../../../utils/format';
import Checkbox from '../../@atom/Checkbox/Checkbox';
import SubTitle from '../../@atom/SubTitle/SubTitle.styles';
import Transition from '../../@atom/Transition/Transition.styles';
import IconOutlineBarButton from '../../@molecule/IconOutlineBarButton/IconOutlineBarButton';
import {
  StyledModal,
  ButtonContainer,
  StyledPointSelect,
  CheckboxContainer,
  PaymentReadyButton,
  DonatorTermLink,
  StyledSubTitle,
  PaymentButtonContainer,
} from './PointChargeModal.styles';

export interface PointChargeModalProps {
  closeModal: () => void;
}

const PointRadio = ({ children, id, value }: any) => {
  return (
    <StyledPointSelect>
      <input type="radio" name="point" id={id} value={value} />
      <label htmlFor={id}>{children}</label>
    </StyledPointSelect>
  );
};

interface PointChargeForm {
  selectedPoint: string | null;
  isTermAgreed: boolean;
  isHowToAgreed: boolean;
  isAdultAgreed: boolean;
}

const PointChargeModal = ({ closeModal }: PointChargeModalProps) => {
  const { accessToken } = useAccessToken();
  const [form, setForm] = useState<PointChargeForm>({
    selectedPoint: null,
    isTermAgreed: false,
    isHowToAgreed: false,
    isAdultAgreed: false,
  });
  const [isAllChecked, _setIsAllChecked] = useState(false);

  const { selectedPoint, isTermAgreed, isHowToAgreed, isAdultAgreed } = form;

  const setIsAllchecked = (value: boolean) => {
    _setIsAllChecked(value);
    setForm({ ...form, isTermAgreed: value, isHowToAgreed: value, isAdultAgreed: value });
  };

  const setIsTermAgreed = (value: boolean) => {
    setForm({ ...form, isTermAgreed: value });
  };
  const setIsHowToAgreed = (value: boolean) => {
    setForm({ ...form, isHowToAgreed: value });
  };
  const setIsAdultAgreed = (value: boolean) => {
    setForm({ ...form, isAdultAgreed: value });
  };

  const setSelectedPoint = (value: string) => {
    setForm({ ...form, selectedPoint: value });
  };

  const pointOptions = [
    { id: 'ITEM_1', value: 1000 },
    { id: 'ITEM_3', value: 3000 },
    { id: 'ITEM_5', value: 5000 },
    { id: 'ITEM_10', value: 10000 },
    { id: 'ITEM_50', value: 50000 },
    { id: 'ITEM_100', value: 100000 },
  ];

  const isValid = selectedPoint && isTermAgreed && isAdultAgreed && isHowToAgreed;

  const onOptionChange = ({ target }: ChangeEvent<HTMLInputElement>) => {
    setSelectedPoint(target.value);
  };

  const [isNext, setIsNext] = useState(false);

  const onPaymentReady = () => {
    if (!isValid) return;

    setIsNext(true);
  };

  const pay = async (pg: string) => {
    const { merchantUid, amount, email, itemName } = await requestPayment(
      selectedPoint as string,
      accessToken
    );
    const { IMP } = window;

    const prodAccountId = 'imp52497817';
    const devAccountId = 'imp61348931';

    const accountId = process.env.NODE_ENV === 'development' ? devAccountId : prodAccountId;

    IMP.init(accountId);

    const IMPRequestPayOption: RequestPayParams = {
      pg,
      pay_method: 'card',
      merchant_uid: merchantUid,
      name: itemName,
      amount,
      buyer_email: email,
    };

    const IMPResponseHandler = async (response: IamportResponse) => {
      if (!response.success) {
        alert(`결제에 실패했습니다. 다시 시도해주세요.\n 에러 내역: ${response.error_msg}`);
        window.close();
        return;
      }

      try {
        const chargeResult = await requestPaymentComplete(response, accessToken);

        alert('결제에 성공했습니다.');
      } catch (error) {
        alert(`결제에 실패했습니다. 다시 시도해주세요. ${error.message}`);
      }

      closeModal();
    };

    IMP.request_pay(IMPRequestPayOption, IMPResponseHandler);
  };

  return (
    <StyledModal onClose={closeModal}>
      {isNext ? (
        <div>
          <SubTitle>충전 금액 선택</SubTitle>
          <div>
            <ButtonContainer onChange={onOptionChange}>
              {pointOptions.map(({ id, value }) => (
                <PointRadio key={id} id={id} value={id}>
                  {toCommaSeparatedString(value)}tp
                </PointRadio>
              ))}
            </ButtonContainer>
            <p>※ 결제 금액에는 모든 세금이 포함되어있습니다 .</p>
            <p>※ 포인트 유효기간: 마지막 사용일로부터 5년 이후</p>
          </div>
          <CheckboxContainer>
            <div>
              <Checkbox
                checked={isAllChecked}
                onChange={({ target }) => setIsAllchecked(target.checked)}
              />
              <span>전체 동의</span>
            </div>
            <div>
              <Checkbox
                checked={isTermAgreed}
                onChange={({ target }) => setIsTermAgreed(target.checked)}
              />
              <span>
                <DonatorTermLink onClick={() => popupTerms('/contracts/donator-policy.html')}>
                  결제 약관
                </DonatorTermLink>
                에 동의합니다. (필수)
              </span>
            </div>
            <div>
              <Checkbox
                checked={isHowToAgreed}
                onChange={({ target }) => setIsHowToAgreed(target.checked)}
              />
              <span>충전 금액과 방법에 대해 확인 및 동의합니다.</span>
            </div>
            <div>
              <Checkbox
                checked={isAdultAgreed}
                onChange={({ target }) => setIsAdultAgreed(target.checked)}
              />
              <span>저는 만 19세 이상의 성인이며 위 결제에 동의합니다.</span>
            </div>
          </CheckboxContainer>
          <PaymentReadyButton onClick={onPaymentReady} disabled={!isValid}>
            결제하기
          </PaymentReadyButton>
        </div>
      ) : (
        <Transition>
          <StyledSubTitle>결제수단을 선택해주세요!</StyledSubTitle>
          <PaymentButtonContainer>
            <IconOutlineBarButton src={KakaoPay} onClick={() => pay('kakaopay')}>
              카카오페이
            </IconOutlineBarButton>
          </PaymentButtonContainer>
        </Transition>
      )}
    </StyledModal>
  );
};

export default PointChargeModal;
