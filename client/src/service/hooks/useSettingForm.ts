import { useEffect, useState } from 'react';

import useUserInfo from './useUserInfo';

interface Setting {
  nickname: string;
  bio: string;
  profileImg: string;
}

const useSettingForm = () => {
  const [form, setForm] = useState<Setting>({ nickname: '', bio: '', profileImg: '' });
  const { userInfo } = useUserInfo();

  const setNickname = (nickname: string) => {
    setForm({ ...form, nickname });
  };

  const setBio = (bio: string) => {
    setForm({ ...form, bio });
  };

  const setProfileImg = (files: FileList) => {
    const reader = new FileReader();

    reader.readAsDataURL(files[0]);

    reader.onloadend = ({ target }) => {
      if (typeof target?.result !== 'string') {
        return alert('파일을 읽는데 실패했습니다. 다시 시도해주세요.');
      }

      setForm({ ...form, profileImg: target.result });
    };
  };

  useEffect(() => {
    if (!userInfo) return;

    setForm(userInfo);
  }, [userInfo]);

  return { form, setNickname, setBio, setProfileImg };
};

export default useSettingForm;
