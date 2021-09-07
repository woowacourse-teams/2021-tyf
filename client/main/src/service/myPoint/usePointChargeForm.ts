import { useState } from 'react';
import { PointChargeForm } from '../../types';

const usePointChargeForm = () => {
  const [form, setForm] = useState<PointChargeForm>({
    selectedItemId: '',
    isTermAgreed: false,
    isHowToAgreed: false,
    isAdultAgreed: false,
  });
  const [isAllChecked, _setIsAllChecked] = useState(false);

  const { selectedItemId, isTermAgreed, isHowToAgreed, isAdultAgreed } = form;

  const setIsAllchecked = (value: boolean) => {
    _setIsAllChecked(value);
    setForm({ ...form, isTermAgreed: value, isHowToAgreed: value, isAdultAgreed: value });
  };

  const setIsTermAgreed = (value: boolean) => {
    setForm({ ...form, isTermAgreed: value });
  };

  const setIsHowToAgreed = (value: boolean) => {
    setForm({ ...form, isHowToAgreed: value });
  };

  const setIsAdultAgreed = (value: boolean) => {
    setForm({ ...form, isAdultAgreed: value });
  };

  const setSelectedItemId = (value: string) => {
    setForm({ ...form, selectedItemId: value });
  };

  const isValid = selectedItemId && isTermAgreed && isAdultAgreed && isHowToAgreed;

  return {
    form,
    isAllChecked,
    setIsAllchecked,
    setIsTermAgreed,
    setIsHowToAgreed,
    setIsAdultAgreed,
    setSelectedItemId,
    isValid,
  };
};

export default usePointChargeForm;
