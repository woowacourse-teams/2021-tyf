import { useHistory } from 'react-router';
import useSettlement from '../../../service/settlement/useSettlement';
import {
  CancelDetail,
  StyledButton,
  StyledSubTitle,
  StyledSettlementRegisterCancelledInfo,
} from './SettlementRegisterCancelledInfo,styles';

const SettlementRegisterCancelledInfo = () => {
  const history = useHistory();

  return (
    <StyledSettlementRegisterCancelledInfo>
      <StyledSubTitle>
        정산 계좌 인증 신청이 반려 되었습니다.
        <br /> 다시 신청 해주세요.
      </StyledSubTitle>
      {/* <CancelDetail>반려사유: {account}</CancelDetail> */}
      <StyledButton
        onClick={() => {
          history.push('/settlement/register');
        }}
      >
        정산 계좌 인증하기
      </StyledButton>
    </StyledSettlementRegisterCancelledInfo>
  );
};

export default SettlementRegisterCancelledInfo;
