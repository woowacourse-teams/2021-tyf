export const throttleGenerator = (time: number) => {
  let timerId: NodeJS.Timer | null = null;

  return (callback: () => void) => {
    if (!timerId) {
      timerId = setTimeout(() => {
        timerId = null;
        callback();
      }, time);
    }
  };
};
