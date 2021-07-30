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
