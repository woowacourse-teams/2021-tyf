import { ChangeEvent, useState } from 'react';

import KakaoPay from '../../../assets/icons/kakao-pay.svg';
import { popupTerms } from '../../../service/@shared/popupTerms';
import useAccessToken from '../../../service/@shared/useAccessToken';
import usePointChargeForm from '../../../service/myPoint/usePointChargeForm';
import { pay } from '../../../service/payment/payment';
import { toCommaSeparatedString } from '../../../utils/format';
import Checkbox from '../../@atom/Checkbox/Checkbox';
import SubTitle from '../../@atom/SubTitle/SubTitle.styles';
import Transition from '../../@atom/Transition/Transition.styles';
import IconOutlineBarButton from '../../@molecule/IconOutlineBarButton/IconOutlineBarButton';
import {
  StyledModal,
  ButtonContainer,
  CheckboxContainer,
  PaymentReadyButton,
  DonatorTermLink,
  StyledSubTitle,
  PaymentButtonContainer,
} from './PointChargeModal.styles';
import PointRadio from './PointRadio/PointRadio';

export interface PointChargeModalProps {
  closeModal: () => void;
}

const pointOptions = [
  { id: 'ITEM_1', value: 1000 },
  { id: 'ITEM_3', value: 3000 },
  { id: 'ITEM_5', value: 5000 },
  { id: 'ITEM_10', value: 10000 },
  { id: 'ITEM_50', value: 50000 },
  { id: 'ITEM_100', value: 100000 },
];

const PointChargeModal = ({ closeModal }: PointChargeModalProps) => {
  const { accessToken } = useAccessToken();
  const [isNext, setIsNext] = useState(false);
  const {
    form,
    isAllChecked,
    isValid,
    setIsAdultAgreed,
    setIsAllchecked,
    setIsHowToAgreed,
    setIsTermAgreed,
    setSelectedItemId,
  } = usePointChargeForm();

  const { selectedItemId, isTermAgreed, isHowToAgreed, isAdultAgreed } = form;

  const onOptionChange = ({ target }: ChangeEvent<HTMLInputElement>) => {
    setSelectedItemId(target.value);
  };

  const onPaymentReady = () => {
    if (!isValid) return;

    setIsNext(true);
  };

  return (
    <StyledModal onClose={closeModal}>
      {isNext ? (
        <Transition>
          <StyledSubTitle>결제수단을 선택해주세요!</StyledSubTitle>
          <PaymentButtonContainer>
            <IconOutlineBarButton
              src={KakaoPay}
              onClick={() => pay('kakaopay', selectedItemId, accessToken, closeModal)}
            >
              카카오페이
            </IconOutlineBarButton>
          </PaymentButtonContainer>
        </Transition>
      ) : (
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
      )}
    </StyledModal>
  );
};

export default PointChargeModal;
