import { atom, selector } from 'recoil';

import { Register } from '../../types';
import { REGISTER } from '../../constants/register';

export const newUserState = atom<Register>({
  key: 'newUserState',
  default: { email: '', nickname: '', oauthType: '', pageName: '' },
});

export const urlNameValidationSelector = selector<string>({
  key: 'urlNameValidationQuery',
  get: ({ get }) => {
    const { pageName } = get(newUserState);

    if (pageName.length < REGISTER.ADDRESS.MIN_LENGTH) {
      return `주소는 최소 ${REGISTER.ADDRESS.MIN_LENGTH}글자 이상이여합니다.`;
    }

    if (REGISTER.ADDRESS.MAX_LENGTH < pageName.length) {
      return `주소는 최대 ${REGISTER.ADDRESS.MAX_LENGTH}글자 이하여야 합니다`;
    }

    if (pageName !== pageName.replace(/ /g, '')) {
      return '주소에는 공백이 존재하면 안됩니다.';
    }

    if (pageName !== pageName.toLowerCase()) {
      return '주소명은 소문자만 가능합니다.';
    }

    const regExp = /^[a-z0-9_\\-]*$/;
    if (!regExp.test(pageName)) {
      return "주소는 영어 소문자, 숫자, '-', '_' 만 가능합니다.";
    }

    return '';
  },
});

export const nickNameValidationSelector = selector<string>({
  key: 'nickNameValidationQuery',
  get: ({ get }) => {
    const { nickname } = get(newUserState);

    if (nickname.length < REGISTER.NICKNAME.MIN_LENGTH) {
      return `닉네임은 최소 ${REGISTER.NICKNAME.MIN_LENGTH}글자 이상이여합니다.`;
    }

    if (REGISTER.NICKNAME.MAX_LENGTH < nickname.length) {
      return `닉네임은 최대 ${REGISTER.NICKNAME.MAX_LENGTH}글자 이하여야 합니다`;
    }

    if (nickname !== nickname.trim()) {
      return '닉네임의 맨 앞과 뒤에는 공백이 불가능합니다.';
    }

    if (nickname !== nickname.replace(/ +/g, ' ')) {
      return '닉네임 사이에 공백은 한칸만 가능합니다.';
    }

    // 한글, 영어, 한자, 일본어, 숫자 매칭
    const regExp = /^[a-zA-Z0-9가-힇ㄱ-ㅎㅏ-ㅣぁ-ゔァ-ヴー々〆〤一-龥]*$/;
    if (!regExp.test(nickname)) {
      return '닉네임은 한글, 영어, 한자, 일본어, 숫자만 가능합니다.';
    }

    return '';
  },
});

// // DB 를 통한 유효성 검증
// export const urlNameDBValidationQuery = selector<string>({
//   key: 'urlNameDBValidationQuery',
//   get: async ({ get }) => {
//     // const { pageName } = get(newUserState);

//     // TODO : 검증 디바운싱 적용.
//     // cors 오류 해결전까지 주석처리
//     try {
//       // await requestValidatePageName(urlName);

//       return '';
//     } catch (error) {
//       return error.message;
//     }
//   },
// });

// export const nickNameDBValidationQuery = selector<string>({
//   key: 'nickNameDBValidationQuery',
//   get: async ({ get }) => {
//     // const { nickname } = get(newUserState);

//     // TODO : 검증 디바운싱 적용.
//     // cors 오류 해결전까지 주석처리
//     try {
//       // await requestValidatePageName(urlName);

//       return '';
//     } catch (error) {
//       return error.message;
//     }
//   },
// });
