import type { Config } from '@jest/types';

const config: Config.InitialOptions = {
  preset: 'ts-jest',
  testEnvironment: 'jsdom',
  setupFilesAfterEnv: ['<rootDir>/src/__test__/jest.setup.ts'],
  moduleNameMapper: {
    '\\.(jpg|jpeg|png|gif|webp|svg|mp4|webm|wav|mp3|m4a|aac)$':
      '<rootDir>/src/__test__/utils/assetsTransformer.js',
  },
};

export default config;
