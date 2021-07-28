import { useState } from 'react';
import { UserInfo } from '../../types';

const S3_URL = 'https://de56jrhz7aye2.cloudfront.net';

type ColorType = 'red' | 'black' | 'yellow' | 'green' | 'blue' | 'purple';

const useURLBanner = (userInfo: UserInfo) => {
  const [color, setColor] = useState<ColorType>('red');

  const bannerURL = `${S3_URL}/banner/${color}.png`;
  const sourceCode = `<a href="${window.location.origin}/creator/${userInfo.pageName}" target="_blank"><img height="40" style="border:0px;height:40px;" src="${bannerURL}" border="0" alt="thank you for button" /></a>`;

  const changeButtonColor = (color: ColorType) => {
    setColor(color);
  };

  const copySourceCode = () => {
    navigator.clipboard.writeText(sourceCode);
    alert('소스코드가 복사되었습니다.');
  };

  return { sourceCode, bannerURL, changeButtonColor, copySourceCode };
};

export default useURLBanner;
