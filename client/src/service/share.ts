import webShare from '../utils/webShareAPI';

export const donationUrlShare = (creatorName: string, pageName: string) =>
  webShare({
    title: `${creatorName}님에게 도네이션하기 | Thank You For ___`,
    text: `${creatorName}님에게 감사를 표시 해보세요!`,
    url: `${window.location.origin}/donation/${pageName}`,
  });
