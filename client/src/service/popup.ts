import { DONATION_POPUP } from '../constants/popup';
import { S3_URL } from '../constants/s3';

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

export const popupTerms = (route: string) => {
  popupWindow(S3_URL + route, {
    width: DONATION_POPUP.WIDTH,
    height: DONATION_POPUP.HEIGHT,
  });
};
