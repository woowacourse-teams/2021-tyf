export const toCommaSeparatedString = (value: number) => {
  if (!value || Number.isNaN(value)) return value + '';

  return value.toLocaleString('en-us');
};

export const secToMin = (seconds: number) => {
  const computedMinutes = Math.floor(seconds / 60);
  const computedSeconds = seconds - computedMinutes * 60;

  return `${computedMinutes}:${computedSeconds.toLocaleString('en-us', {
    minimumIntegerDigits: 2,
  })}`;
};
