import { useEffect, useState } from 'react';

// NOTE: 초 단위

const useTimer = (initialTime: number) => {
  const [remainTime, setRemainTime] = useState(initialTime);
  const [timerId, setTimer] = useState<NodeJS.Timer | null>(null);

  useEffect(() => {
    const intervalId = setInterval(() => {
      setRemainTime((prev) => prev - 1);
    }, 1000);

    setTimer(intervalId);

    return () => clearInterval(intervalId);
  }, []);

  if (remainTime <= 0) clearInterval(timerId!);

  const resetTimer = () => {
    setRemainTime(initialTime);
  };

  return { remainTime, resetTimer };
};

export default useTimer;
