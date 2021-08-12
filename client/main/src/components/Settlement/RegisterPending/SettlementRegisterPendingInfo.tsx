import { useHistory } from 'react-router-dom';
import Button from '../../@atom/Button/Button.styles';
import SubTitle from '../../@atom/SubTitle/SubTitle.styles';
import { StyledSettlementRegisterPendingInfo } from './SettlementRegisterPendingInfo,styles';

const SettlementRegisterPendingInfo = () => {
  const history = useHistory();

  return (
    <StyledSettlementRegisterPendingInfo>
      <SubTitle>
        보내주신 요청을 확인 중입니다.
        <br /> 잠시만 기다려주시면 빨리 확인하겠습니다.
      </SubTitle>
      <Button onClick={() => history.goBack()}>뒤로가기</Button>
    </StyledSettlementRegisterPendingInfo>
  );
};

export default SettlementRegisterPendingInfo;
