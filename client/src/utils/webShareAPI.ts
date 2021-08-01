const webShare = (data: ShareData) => {
  return window.navigator.share(data);
};

export default webShare;
