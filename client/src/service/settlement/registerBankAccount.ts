import { SettlementAccountForm } from '../../types';
import { requestRegisterBankAccount } from '../@request/settlement';

export const registerBankAccount = async (form: SettlementAccountForm) => {
  try {
    await requestRegisterBankAccount(form);

    alert('계정정보 등록에 성공했습니다!');
  } catch (error) {
    alert('계좌정보를 등록하는데 실패했습니다.');
  }
};
