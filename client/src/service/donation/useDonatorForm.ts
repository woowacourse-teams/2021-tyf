import { useState } from 'react';
import { useRecoilState } from 'recoil';
import { donationState } from '../@state/donation';

const emailRegExp =
  /^(([^<>()[\].,;:\s@"]+(\.[^<>()[\].,;:\s@"]+)*)|(".+"))@(([^<>()[\].,;:\s@"]+\.)+[^<>()[\].,;:\s@"]{2,})$/i;

const useDonatorForm = () => {
  const [form, setForm] = useRecoilState(donationState);
  const [isTermChecked, setIsTermChecked] = useState(false);

  const isValidEmail = emailRegExp.test(form.email);

  const setEmail = (email: string) => {
    setForm({ ...form, email });
  };

  return { form, setEmail, isValidEmail, isTermChecked, setIsTermChecked };
};

export default useDonatorForm;
