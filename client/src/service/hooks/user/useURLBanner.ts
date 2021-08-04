import { useState } from 'react';
import { S3_URL } from '../../../constants/s3';
import { UserInfo } from '../../../types';

type ColorType = 'red' | 'black' | 'yellow' | 'green' | 'blue' | 'purple';

const useURLBanner = (userInfo: UserInfo) => {
  const [color, setColor] = useState<ColorType>('red');

  const bannerURL = `${S3_URL}/banner/${color}.svg`;
  const sourceCode = `<a href="${window.location.origin}/creator/${userInfo.pageName}" target="_blank"><img height="40" style="border:0px;height:40px;" src="${bannerURL}" border="0" alt="thank you for button" /></a>`;

  const changeButtonColor = (color: ColorType) => {
    setColor(color);
  };

  const copySourceCode = async () => {
    try {
      await navigator.clipboard.writeText(sourceCode);
      alert('소스코드가 복사되었습니다.');
    } catch (error) {
      alert('소스코드 복사에 실패했습니다.');
    }
  };

  return { sourceCode, bannerURL, changeButtonColor, copySourceCode };
};

export default useURLBanner;
