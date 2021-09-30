import { SETTING_ERROR_MESSAGE } from '../../constants/error';
import { requestUpdateBio, requestUpdateNickname, requestUpdateProfileImg } from '../@request/user';
import useAccessToken from '../@shared/useAccessToken';
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
      const { errorCode }: { errorCode: keyof typeof SETTING_ERROR_MESSAGE } = error.response.data;
      const errorMessage =
        SETTING_ERROR_MESSAGE[errorCode] ??
        '사용자 정보 변경에 실패했습니다. 문제가 지속되면 고객센터로 문의해주세요.';
      alert(errorMessage);
    }
  };

  return { submit };
};

export default useSetting;
