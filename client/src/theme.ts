import PALETTE from './constants/palette';

const theme = {
  primary: { base: PALETTE.CORAL_400, dimmed: PALETTE.CORAL_700, blackened: PALETTE.CORAL_900 },

  color: {
    main: PALETTE.BLACK_400,
    sub: PALETTE.WHITE_400,
    placeholder: PALETTE.GRAY_400,
    border: PALETTE.GRAY_300,
  },
} as const;

export type ThemeType = typeof theme;
