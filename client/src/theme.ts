import PALETTE from './constants/palette';

export const theme = {
  primary: { base: PALETTE.CORAL_400, dimmed: PALETTE.CORAL_700, blackened: PALETTE.CORAL_900 },

  color: {
    main: PALETTE.BLACK_400,
    sub: PALETTE.WHITE_400,
  },
} as const;

export type ThemeType = typeof theme;
