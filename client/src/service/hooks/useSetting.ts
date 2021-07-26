import { UserInfo } from '../../types';
import { requestUpdateBio, requestUpdateNickname, requestUpdateProfileImg } from '../request/user';
import useAccessToken from './useAccessToken';
import useUserInfo from './useUserInfo';

type SubmitArgsType = Pick<UserInfo, 'profileImg' | 'nickname' | 'bio'>;

const useSetting = () => {
  const { accessToken } = useAccessToken();
  const { userInfo } = useUserInfo();

  const submit = async ({ profileImg, nickname, bio }: SubmitArgsType) => {
    try {
      if (profileImg !== userInfo?.profileImg) {
        await requestUpdateProfileImg(profileImg, accessToken);
      }

      if (nickname !== userInfo?.nickname) {
        await requestUpdateNickname(nickname, accessToken);
      }

      if (bio !== userInfo?.bio) {
        await requestUpdateBio(bio, accessToken);
      }

      window.location.reload();
    } catch (error) {
      alert('사용자 정보 변경에 실패했습니다.');
    }
  };

  return { submit };
};

export default useSetting;
