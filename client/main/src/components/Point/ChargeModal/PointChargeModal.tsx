import { ChangeEvent, useState } from 'react';

import KakaoPay from '../../../assets/icons/kakao-pay.svg';
import { popupTerms } from '../../../service/@shared/popupTerms';
import useAccessToken from '../../../service/@shared/useAccessToken';
import useLoadScriptEffect from '../../../service/myPoint/useLoadScriptEffect';
import usePointChargeForm from '../../../service/myPoint/usePointChargeForm';
import { pay } from '../../../service/payment/payment';
import { toCommaSeparatedString } from '../../../utils/format';
import Checkbox from '../../@atom/Checkbox/Checkbox.styles';
import SubTitle from '../../@atom/SubTitle/SubTitle.styles';
import Transition from '../../@atom/Transition/Transition.styles';
import IconOutlineBarButton from '../../@molecule/IconOutlineBarButton/IconOutlineBarButton';
import Spinner from '../../Spinner/Spinner';
import {
  StyledModal,
  ButtonContainer,
  CheckboxContainer,
  PaymentReadyButton,
  DonatorTermLink,
  StyledSubTitle,
  PaymentButtonContainer,
  SelectAllText,
  CheckboxContainerList,
  Caution,
} from './PointChargeModal.styles';
import PointRadio from './PointRadio/PointRadio';

export interface PointChargeModalProps {
  closeModal: () => void;
}

const pointOptions = [
  { id: 'ITEM_1', point: 1000, price: 1000 },
  { id: 'ITEM_3', point: 3000, price: 3000 },
  { id: 'ITEM_5', point: 5000, price: 5000 },
  { id: 'ITEM_10', point: 10000, price: 10000 },
  { id: 'ITEM_50', point: 50000, price: 50000 },
  { id: 'ITEM_100', point: 100000, price: 100000 },
];

const totalPrice = (selectedId: string) => {
  const FEE_RATE = 0.1;
  const selectedPrice = pointOptions.find(({ id }) => id === selectedId)?.price || 0;

  return Math.floor(selectedPrice * (1 + FEE_RATE));
};

const PointChargeModal = ({ closeModal }: PointChargeModalProps) => {
  const { accessToken } = useAccessToken();
  const [isNext, setIsNext] = useState(false);
  const [isPaying, setIsPaying] = useState(false);

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

  useLoadScriptEffect();

  return (
    <StyledModal onClose={closeModal}>
      {isNext ? (
        <Transition>
          <StyledSubTitle>??????????????? ??????????????????!</StyledSubTitle>
          <PaymentButtonContainer>
            <IconOutlineBarButton
              src={KakaoPay}
              onClick={() => {
                setIsPaying(true);
                pay('kakaopay', selectedItemId, accessToken, () => {
                  setIsPaying(false);
                  window.location.reload();
                });
              }}
            >
              ???????????????
            </IconOutlineBarButton>
          </PaymentButtonContainer>
        </Transition>
      ) : (
        <>
          <SubTitle>?????? ????????? ??????</SubTitle>
          <div>
            <ButtonContainer onChange={onOptionChange}>
              {pointOptions.map(({ id, point }) => (
                <PointRadio key={id} id={id} value={id}>
                  {toCommaSeparatedString(point)} tp
                </PointRadio>
              ))}
            </ButtonContainer>
            <Caution>??? ?????? ???????????? ?????? ????????? ???????????????????????? .</Caution>
            <Caution>??? ????????? ????????????: ????????? ?????????????????? 5??? ??????</Caution>
          </div>
          <CheckboxContainerList>
            <CheckboxContainer>
              <Checkbox
                checked={isAllChecked}
                onChange={({ target }) => setIsAllchecked(target.checked)}
              />
              <SelectAllText>?????? ??????</SelectAllText>
            </CheckboxContainer>
            <CheckboxContainer>
              <Checkbox
                checked={isTermAgreed}
                onChange={({ target }) => setIsTermAgreed(target.checked)}
              />
              <span>
                <DonatorTermLink onClick={() => popupTerms('/contracts/donator-policy.html')}>
                  ?????? ??????
                </DonatorTermLink>
                ??? ???????????????.
              </span>
            </CheckboxContainer>
            <CheckboxContainer>
              <Checkbox
                checked={isHowToAgreed}
                onChange={({ target }) => setIsHowToAgreed(target.checked)}
              />
              ?????? ????????? ????????? ?????? ?????? ??? ???????????????.
            </CheckboxContainer>
            <CheckboxContainer>
              <Checkbox
                checked={isAdultAgreed}
                onChange={({ target }) => setIsAdultAgreed(target.checked)}
              />
              <span>?????? ??? 19??? ????????? ???????????? ??? ????????? ???????????????.</span>
            </CheckboxContainer>
          </CheckboxContainerList>
          <PaymentReadyButton onClick={onPaymentReady} disabled={!isValid}>
            {toCommaSeparatedString(totalPrice(selectedItemId))}??? ????????????
          </PaymentReadyButton>
        </>
      )}
      {isPaying && <Spinner />}
    </StyledModal>
  );
};

export default PointChargeModal;
