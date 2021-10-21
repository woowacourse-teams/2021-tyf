import { useState } from 'react';

import { SettlementAccountForm } from '../../types';

const FIRST_REGISTRATION_NUMBER_MAX_LENGTH = 6;
const SECOND_REGISTRATION_NUMBER_MAX_LENGTH = 7;

const useSettlementAccountForm = () => {
  const [form, setForm] = useState<SettlementAccountForm>({
    accountHolder: '',
    accountNumber: '',
    residentRegistrationNumber: ['', ''],
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

  const setResidentRegistrationNumberFront = (value: string) => {
    if (isNaN(Number(value)) || value.length > FIRST_REGISTRATION_NUMBER_MAX_LENGTH) return;

    setForm({ ...form, residentRegistrationNumber: [value, form.residentRegistrationNumber[1]] });
  };

  const setResidentRegistrationNumberRear = (value: string) => {
    if (isNaN(Number(value)) || value.length > SECOND_REGISTRATION_NUMBER_MAX_LENGTH) return;

    setForm({ ...form, residentRegistrationNumber: [form.residentRegistrationNumber[0], value] });
  };

  const isFormValid =
    form.accountHolder &&
    form.bank &&
    form.accountNumber &&
    form.bankbookImage &&
    form.residentRegistrationNumber[0] &&
    form.residentRegistrationNumber[1];

  return {
    form,
    setAccountHolder,
    setBank,
    setAccountNumber,
    setBankbookImage,
    setResidentRegistrationNumberFront,
    setResidentRegistrationNumberRear,
    isFormValid,
  };
};

export default useSettlementAccountForm;
