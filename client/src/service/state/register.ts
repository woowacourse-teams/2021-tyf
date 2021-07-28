import { atom } from 'recoil';

import { Register } from '../../types';

export const newUserState = atom<Register>({
  key: 'newUserState',
  default: { email: '', nickname: '', oauthType: '', pageName: '' },
});
