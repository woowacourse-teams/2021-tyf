export const popupWindow = (path: string, option?: { width?: number; height?: number }) => {
  let optionString = '';

  if (option?.width) {
    optionString += `width=${option.width},`;
  }

  if (option?.height) {
    optionString += `height=${option.height}`;
  }

  window.open(path, '_blank', optionString);
};
