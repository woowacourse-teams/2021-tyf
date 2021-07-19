export const donationUrlShare = (creatorName: string, pageName: string) => {
  const url = `${window.location.origin}/donation/${pageName}`;
  const title = `${creatorName}님에게 도네이션하기 | Thank You For ___`;
  const text = `${creatorName}님에게 감사를 표시 해보세요!`;

  if (window.navigator.share) {
    return window.navigator.share({ title, text, url });
  }

  return navigator.clipboard.writeText(url);
};
