import { useState } from 'react';

import { SettlementAccountForm } from '../../types';

const useSettlementAccountForm = () => {
  const [form, setForm] = useState<SettlementAccountForm>({
    accountHolder: '',
    accountNumber: '',
    bank: null,
    bankbookImage: null,
  });

  const setAccountHolder = (value: string) => {
    setForm({ ...form, accountHolder: value.trim() });
  };

  const setBank = (bank: string) => {
    setForm({ ...form, bank });
  };

  const setAccountNumber = (value: string) => {
    if (isNaN(Number(value))) return;

    setForm({ ...form, accountNumber: value.trim() });
  };

  const setBankbookImage = (bankbookImage: File) => {
    setForm({ ...form, bankbookImage });
  };

  const isFormValid = form.accountHolder && form.bank && form.accountNumber && form.bankbookImage;

  return { form, setAccountHolder, setBank, setAccountNumber, setBankbookImage, isFormValid };
};

export default useSettlementAccountForm;
