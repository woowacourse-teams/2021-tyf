import { apiClient, authorizationHeader } from '../../API';
import { UserInfo } from '../../types';

export const requestUserInfo = (accessToken: string): Promise<UserInfo> => {
  return apiClient.get('/members/me', authorizationHeader(accessToken));
};

export const requestUpdateProfileImg = (
  profileImg: File,
  accessToken: string
): Promise<{ profileUrl: string }> => {
  const formData = new FormData();

  formData.append('multipartFile', profileImg, profileImg.name);

  return apiClient.put('/members/profile', formData, authorizationHeader(accessToken));
};

export const requestUpdateNickname = (nickname: string, accessToken: string) => {
  return apiClient.put('/members/me/nickname', { nickname }, authorizationHeader(accessToken));
};

export const requestUpdateBio = (bio: string, accessToken: string) => {
  return apiClient.put('/members/me/bio', { bio }, authorizationHeader(accessToken));
};
