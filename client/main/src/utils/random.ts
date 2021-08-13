export const getRandomNumber = (min = 0, max = Number.MAX_SAFE_INTEGER) => {
  return Math.floor(Math.random() * (max - min)) + min;
};
