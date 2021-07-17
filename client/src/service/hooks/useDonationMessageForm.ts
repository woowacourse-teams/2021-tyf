import { useState } from 'react';
import { DonationMessage } from '../../types';

const useDonationMessageForm = () => {
  const [form, setForm] = useState<DonationMessage>({ name: '', message: '' });
  const [isPrivate, setIsPrivate] = useState(false);

  const setMessage = (message: string) => {
    if (message.length > 200) return;

    setForm({ ...form, message });
  };

  const setName = (name: string) => {
    setForm({ ...form, name });
  };

  return { form, isPrivate, setMessage, setName, setIsPrivate };
};

export default useDonationMessageForm;
