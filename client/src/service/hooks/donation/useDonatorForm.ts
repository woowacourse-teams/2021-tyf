import { useState } from 'react';
import { useRecoilState } from 'recoil';
import { donationState } from '../../state/donation';

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

// email state에 저장 및 읽기

// 결제약관 동의 여부
// 유효성 검증 완료, 동의 여부 - 다음버튼 활성화 용도

// TODO :email 유효섬 검증 (미루기)
