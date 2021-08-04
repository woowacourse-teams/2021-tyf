import { useState } from 'react';

import { MAX_MESSAGE_LENGTH } from '../../../constants/donation';
import { DonationMessage } from '../../../types';

const useDonationMessageForm = () => {
  const [form, setForm] = useState<DonationMessage>({ name: '', message: '' });
  const [isPrivate, setIsPrivate] = useState(false);

  const setMessage = (message: string) => {
    if (message.length > MAX_MESSAGE_LENGTH) return;

    setForm({ ...form, message });
  };

  const setName = (name: string) => {
    setForm({ ...form, name });
  };

  return { form, isPrivate, setMessage, setName, setIsPrivate };
};

export default useDonationMessageForm;
