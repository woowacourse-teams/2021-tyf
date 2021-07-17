import { useState } from 'react';

const useTerms = () => {
  const [termsChecked, setTermsChecked] = useState({
    termsOfService: false,
    personalInformationUsage: false,
  });

  const isAllTermsChecked = Object.values(termsChecked).every((isChecked) => isChecked === true);

  const toggleTermChecked = ({ name, checked }: { name: string; checked: boolean }) => {
    setTermsChecked({ ...termsChecked, [name]: checked });
  };

  const toggleAllTermsChecked = (event: React.ChangeEvent<HTMLInputElement>) => {
    const toggleAll = Object.keys(termsChecked).reduce((acc, term) => {
      return Object.assign(acc, { [term]: event.target.checked });
    }, {} as typeof termsChecked);

    setTermsChecked(toggleAll);
  };

  return { termsChecked, isAllTermsChecked, toggleTermChecked, toggleAllTermsChecked };
};

export default useTerms;
