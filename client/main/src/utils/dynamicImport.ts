export const loadScript = (src: string) => {
  const $script: HTMLScriptElement = document.createElement('script');
  $script.src = src;
  $script.defer = true;
  $script.async = false;
  document.head.append($script);
};
