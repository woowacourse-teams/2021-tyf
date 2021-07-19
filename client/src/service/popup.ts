export const popupWindow = (path: string, option?: string) => {
  window.open(window.location.origin + path, '_blank', option);
};
