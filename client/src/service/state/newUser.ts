import { atom, selector } from 'recoil';

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
