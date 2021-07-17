import { atom, selector } from 'recoil';

import { Register } from '../../types';
import { REGISTER } from '../../constants/constant';

export const newUserState = atom<Register>({
  key: 'newUserState',
  default: { email: '', nickName: '', oauthType: '', urlName: '' },
});

export const urlNameValidationSelector = selector({
  key: 'urlNameValidationQuery',
  get: ({ get }) => {
    const { urlName } = get(newUserState);

    if (urlName.length < REGISTER.ADDRESS.MIN_LENGTH) {
      return `주소는 최소 ${REGISTER.ADDRESS.MIN_LENGTH}글자 이상이여합니다.`;
    }

    if (REGISTER.ADDRESS.MAX_LENGTH < urlName.length) {
      return `주소는 최대 ${REGISTER.ADDRESS.MAX_LENGTH}글자 이하여야 합니다`;
    }

    if (urlName !== urlName.replace(/ /g, '')) {
      return '주소에는 공백이 존재하면 안됩니다.';
    }

    if (urlName !== urlName.toLowerCase()) {
      return '주소명은 소문자만 가능합니다.';
    }

    const regExp = /^[a-z0-9_\\-]*$/;
    if (!regExp.test(urlName)) {
      return "주소는 영어 소문자, 숫자, '-', '_' 만 가능합니다.";
    }

    return '';
  },
});

export const nickNameValidationSelector = selector({
  key: 'nickNameValidationQuery',
  get: ({ get }) => {
    const { nickName } = get(newUserState);

    if (nickName.length < REGISTER.NICKNAME.MIN_LENGTH) {
      return `닉네임은 최소 ${REGISTER.NICKNAME.MIN_LENGTH}글자 이상이여합니다.`;
    }

    if (REGISTER.NICKNAME.MAX_LENGTH < nickName.length) {
      return `닉네임은 최대 ${REGISTER.NICKNAME.MAX_LENGTH}글자 이하여야 합니다`;
    }

    if (nickName !== nickName.trim()) {
      return '닉네임의 맨 앞과 뒤에는 공백이 불가능합니다.';
    }

    if (nickName !== nickName.replace(/ +/g, ' ')) {
      return '닉네임 사이에 공백은 한칸만 가능합니다.';
    }

    // 한글, 영어, 한자, 일본어, 숫자 매칭
    const regExp = /^[a-zA-Z0-9가-힇ㄱ-ㅎㅏ-ㅣぁ-ゔァ-ヴー々〆〤一-龥]*$/;
    if (!regExp.test(nickName)) {
      return '닉네임은 한글, 영어, 한자, 일본어, 숫자만 가능합니다.';
    }

    return '';
  },
});

// DB 를 통한 유효성 검증
export const urlNameDBValidationQuery = selector<string>({
  key: 'urlNameDBValidationQuery',
  get: async ({ get }) => {
    const { urlName } = get(newUserState);

    // TODO : 검증 디바운싱 적용.
    // cors 오류 해결전까지 주석처리
    try {
      // await requestValidatePageName(urlName);

      return '';
    } catch (error) {
      return error.message;
    }
  },
});

export const nickNameDBValidationQuery = selector<string>({
  key: 'nickNameDBValidationQuery',
  get: async ({ get }) => {
    const { nickName } = get(newUserState);

    // TODO : 검증 디바운싱 적용.
    // cors 오류 해결전까지 주석처리
    try {
      // await requestValidatePageName(urlName);

      return '';
    } catch (error) {
      return error.message;
    }
  },
});
