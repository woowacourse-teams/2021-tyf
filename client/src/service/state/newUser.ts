import { atom, selector } from 'recoil';
import { apiClient } from '../../API';
import { requestValidatePageName } from '../request/register';

export const newUserState = atom({
  key: 'newUserState',
  default: { email: '', nickName: '', oauthType: '', urlName: '' },
});

export const urlNameValidationQuery = selector({
  key: 'urlNameValidationQuery',
  get: ({ get }) => {
    const { urlName } = get(newUserState);

    if (urlName.length < 3) {
      return '주소는 최소 3글자 이상이여합니다.';
    }

    if (20 < urlName.length) {
      return '주소는 최대 20글자 이하여야 합니다';
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

export const urlNameDBValidationQuery = selector({
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
