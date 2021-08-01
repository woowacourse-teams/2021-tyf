export const donationUrlShare = (creatorName: string, pageName: string) => {
  const url = `${window.location.origin}/creator/${pageName}`;
  const title = `${creatorName}님에게 도네이션하기 | Thank You For ___`;
  const text = `${creatorName}님에게 감사를 표시 해보세요!`;

  if (window.navigator.share) {
    return window.navigator.share({ title, text, url });
  }

  alert('도네이션 URL이 복사되었습니다.');

  return navigator.clipboard.writeText(url);
};
