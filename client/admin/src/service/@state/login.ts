import { atom } from 'recoil';
import { STORAGE_KEY } from '../../constants/storage';
import { getLocalStorageItem } from '../../utils/storage';

const getAccessToken = () => {
  const token = getLocalStorageItem(STORAGE_KEY.ACCESS_TOKEN);

  if (token) return token;
  return '';
};

export const accessTokenState = atom<string>({
  key: 'accessTokenState',
  default: getAccessToken(),
});
