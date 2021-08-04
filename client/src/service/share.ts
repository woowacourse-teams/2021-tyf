export const donationUrlShare = async (creatorName: string, pageName: string) => {
  const url = `${window.location.origin}/creator/${pageName}`;
  const title = `${creatorName}님에게 도네이션하기 | Thank You For ___`;
  const text = `${creatorName}님에게 감사를 표시 해보세요!`;

  try {
    if (window.navigator.share) {
      return window.navigator.share({ title, text, url });
    }

    await navigator.clipboard.writeText(url);

    alert('도네이션 URL이 복사되었습니다.');
  } catch (error) {
    alert('도네이션 URL을 복사하는데 실패했습니다.');
  }
};
