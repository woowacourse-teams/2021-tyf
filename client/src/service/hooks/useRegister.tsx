import { useState } from 'react';

const useRegister = () => {
  const [termsChecked, setTermsChecked] = useState({
    termsOfService: false,
    personalInformationUsage: false,
  });

  const toggleTermChecked = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, checked } = event.target;

    setTermsChecked({ ...termsChecked, [name]: checked });
  };

  const toggleAllTermsChecked = (event: React.ChangeEvent<HTMLInputElement>) => {
    const toggleAll = Object.keys(termsChecked).reduce((acc, term) => {
      return Object.assign(acc, { [term]: event.target.checked });
    }, {} as typeof termsChecked);

    setTermsChecked(toggleAll);
  };

  return { termsChecked, toggleTermChecked, toggleAllTermsChecked };
};

export default useRegister;
