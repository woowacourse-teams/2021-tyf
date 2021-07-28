export const debounceGenerator = (time: number) => {
  let timerId: NodeJS.Timer | null = null;

  return (callback: () => void) => {
    if (timerId) {
      clearTimeout(timerId);
    }

    timerId = setTimeout(callback, time);
  };
};
