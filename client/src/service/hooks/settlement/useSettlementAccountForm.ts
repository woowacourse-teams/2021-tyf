import { useState } from 'react';

import { SettlementAccountForm } from '../../../types';

const useSettlementAccountForm = () => {
  const [form, setForm] = useState<SettlementAccountForm>({
    name: '',
    bank: null,
    accountNumber: '',
    bankAccountImage: null,
  });

  const setName = (name: string) => {
    setForm({ ...form, name });
  };

  const setBank = (bank: string) => {
    setForm({ ...form, bank });
  };

  const setAccountNumber = (accountNumber: string) => {
    const lastIndex = accountNumber.slice(-1)[0];
    if (lastIndex !== '-' && isNaN(Number(lastIndex))) return;

    setForm({ ...form, accountNumber });
  };

  const setBankAccountImage = (bankAccountImage: File) => {
    setForm({ ...form, bankAccountImage });
  };

  const isFormValid = form.name && form.bank && form.accountNumber && form.bankAccountImage;

  return { form, setName, setBank, setAccountNumber, setBankAccountImage, isFormValid };
};

export default useSettlementAccountForm;
