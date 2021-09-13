import { atom } from 'recoil';

const getAccessToken = () => {
  const token = sessionStorage.getItem('adminToken');

  if (token) return token;
  return '';
};

export const accessTokenState = atom<string>({
  key: 'accessTokenState',
  default: getAccessToken(),
});
