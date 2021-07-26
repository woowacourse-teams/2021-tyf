import { apiClient, authorizationHeader, multipartAuthorizationHeader } from '../../API';
import { UserInfo } from '../../types';

export const requestUserInfo = (accessToken: string): Promise<UserInfo> => {
  return apiClient.get('/members/me', authorizationHeader(accessToken));
};

export const requestUpdateProfileImg = (
  profileImg: string,
  accessToken: string
): Promise<{ profileUrl: string }> => {
  return apiClient.put(
    '/members/profile',
    { multipartFile: profileImg },
    multipartAuthorizationHeader(accessToken)
  );
};

// TODO: endpoint 나중에 바꿈

export const requestUpdateNickname = (nickname: string, accessToken: string) => {
  return apiClient.put('/members/profile', { nickname }, authorizationHeader(accessToken));
};

export const requestUpdateBio = (bio: string, accessToken: string) => {
  return apiClient.put('/members/profile', { bio }, authorizationHeader(accessToken));
};
