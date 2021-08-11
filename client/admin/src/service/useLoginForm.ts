import { ChangeEvent, useState } from 'react';

const useLoginForm = () => {
  const [form, setForm] = useState({
    id: '',
    pwd: '',
  });

  const onChange = ({ target }: ChangeEvent<HTMLInputElement>) => {
    setForm({ ...form, [target.name]: target.value });
  };

  const isValidForm = form.id && form.pwd;

  return { form, onChange, isValidForm };
};

export default useLoginForm;
