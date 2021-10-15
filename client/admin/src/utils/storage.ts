export const getLocalStorageItem = (key: string) => {
  const item = localStorage.getItem(key);

  if (!item) return null;

  try {
    return JSON.parse(item);
  } catch (error) {
    return item;
  }
};

export const setLocalStorageItem = (key: string, item: unknown) => {
  localStorage.setItem(key, JSON.stringify(item));
};
