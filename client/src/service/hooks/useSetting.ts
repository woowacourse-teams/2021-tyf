import { requestUpdateBio, requestUpdateNickname, requestUpdateProfileImg } from '../request/user';
import useAccessToken from './useAccessToken';
import useUserInfo from './useUserInfo';

interface SubmitArgsType {
  profileImage: string;
  profileImgData: File | null;
  nickname: string;
  bio: string;
}

const useSetting = () => {
  const { accessToken } = useAccessToken();
  const { userInfo } = useUserInfo();

  const submit = async ({ profileImage, profileImgData, nickname, bio }: SubmitArgsType) => {
    try {
      if (profileImgData && profileImage !== userInfo?.profileImage) {
        await requestUpdateProfileImg(profileImgData, accessToken);
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
