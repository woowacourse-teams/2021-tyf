import { useEffect, useState } from 'react';

import useUserInfo from './useUserInfo';

interface Setting {
  nickname: string;
  bio: string;
  profileImgData: File | null;
  profileImage: string;
}

const useSettingForm = () => {
  const [form, setForm] = useState<Setting>({
    nickname: '',
    bio: '',
    profileImage: '',
    profileImgData: null,
  });
  const { userInfo } = useUserInfo();

  const setNickname = (nickname: string) => {
    setForm({ ...form, nickname });
  };

  const setBio = (bio: string) => {
    setForm({ ...form, bio });
  };

  const setProfileImg = (profileImgData: File) => {
    const reader = new FileReader();

    const mb = 1024 * 1024;
    const { type, size } = profileImgData;

    // gif 최대 용량 1mb 제한
    if (type === 'image/gif' && size > mb) {
      alert('gif 파일의 크기는 최대 1mb 이여야합니다.');
      return;
    }

    // 이미지 최대 용량 2mb 제한
    if (size > 2 * mb) {
      alert('이미지 파일의 크기는 최대 2mb 이하여야합니다.');
      return;
    }

    reader.readAsDataURL(profileImgData);

    reader.onload = ({ target }) => {
      if (!target) return alert('파일을 불러오는데 실패했습니다.');

      setForm({ ...form, profileImgData, profileImage: target.result as string });
    };
  };

  useEffect(() => {
    if (!userInfo) return;

    setForm({ ...userInfo, profileImgData: null });
  }, [userInfo]);

  return { form, setNickname, setBio, setProfileImg };
};

export default useSettingForm;
