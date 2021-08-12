import { useState } from 'react';

import { SettlementAccountForm } from '../../types';

const useSettlementAccountForm = () => {
  const [form, setForm] = useState<SettlementAccountForm>({
    accountHolder: '',
    accountNumber: '',
    bank: null,
    bankbookImage: null,
  });

  const setAccountHolder = (accountHolder: string) => {
    setForm({ ...form, accountHolder });
  };

  const setBank = (bank: string) => {
    setForm({ ...form, bank });
  };

  const setAccountNumber = (accountNumber: string) => {
    if (isNaN(Number(accountNumber))) return;

    setForm({ ...form, accountNumber });
  };

  const setBankbookImage = (bankbookImage: File) => {
    setForm({ ...form, bankbookImage });
  };

  const isFormValid = form.accountHolder && form.bank && form.accountNumber && form.bankbookImage;

  return { form, setAccountHolder, setBank, setAccountNumber, setBankbookImage, isFormValid };
};

export default useSettlementAccountForm;
