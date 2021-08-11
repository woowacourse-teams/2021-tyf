import { DONATION_POPUP } from '../../constants/popup';
import { S3_URL } from '../../constants/s3';
import { popupWindow } from '../../utils/popup';

export const popupTerms = (route: string) => {
  popupWindow(S3_URL + route, {
    width: DONATION_POPUP.WIDTH,
    height: DONATION_POPUP.HEIGHT,
  });
};
