export const SIZE = {
  MOBILE_MIN: 320,
  MOBILE_MAX: 424,
  DESKTOP_LARGE: 800,
  DESKTOP_MAX: 1024, // NOTE: 데스크탑 컨텐츠 max width
};

export const DEVICE = {
  DESKTOP: `(min-width: ${SIZE.MOBILE_MAX}px)`,
  DESKTOP_LARGE: `(min-width: ${SIZE.DESKTOP_LARGE}px)`,
};
