import { useState } from 'react';

const checkAddressValid = (address: string) => {
  // 최대 길이 제한 (20글자)
  if (address.length < 3 || 20 < address.length)
    return '주소는 최소 3글자 이상, 최대 20글자 이하여야 합니다.';

  // 공백 불가능
  if (address !== address.replace(/ /g, '')) return '주소에는 공백이 존재하면 안됩니다.';

  // 소문자만 가능
  if (address !== address.toLowerCase()) return '주소명은 소문자만 가능합니다.';

  // 영어 숫자 only, -, _ 가능\
  const regExp = /^[a-z0-9_\\-]*$/;
  if (!regExp.test(address)) return "주소는 영어 소문자, 숫자, '-', '_' 만 가능합니다.";

  return '';
};

const useRegister = () => {
  const [address, setAddress] = useState('');

  const addressErrorMessage = checkAddressValid(address);
  const isValidAddress = !addressErrorMessage;

  const onChangeAddress = ({ value }: { value: string }) => {
    setAddress(value);
  };

  return { address, addressErrorMessage, isValidAddress, onChangeAddress };
};

export default useRegister;
