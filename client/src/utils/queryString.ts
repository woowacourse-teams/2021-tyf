export const getQueryVariable = (target: string) => {
  const queryString = window.location.search?.substring(1);

  if (!queryString) return null;

  const found = queryString.split('&').find((query) => {
    const [key] = query.split('=');

    return key === target;
  });

  return found ? found.split('=')[1] : null;
};
