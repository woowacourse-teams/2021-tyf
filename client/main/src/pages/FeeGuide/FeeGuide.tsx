import { useHistory } from 'react-router';
import SubTitle from '../../components/@atom/SubTitle/SubTitle.styles';
import Title from '../../components/@atom/Title/Title.styles';
import OutlineButton from '../../components/@molecule/OutlineButton/OutlineButton.styles';
import { StyledTemplate, FeeTable, KakaoIcon } from './FeeGuide.styles';

const FeeGuide = () => {
  const history = useHistory();

  return (
    <StyledTemplate>
      <SubTitle>정산 시 수수료 안내</SubTitle>
      <FeeTable>
        <tbody>
          <tr>
            <td>원천세 등의 세금</td>
            <td>3.3%</td>
          </tr>
          <tr>
            <td>
              결제대행사 수수료
              <br />
              <KakaoIcon />
            </td>
            <td>3.2%</td>
          </tr>
          <tr>
            <td>
              Thank You For ___ 수수료
              <br />
              (서버유지비 등 운영비)
            </td>
            <td>0.5%</td>
          </tr>
        </tbody>
      </FeeTable>
      <OutlineButton onClick={history.goBack}>돌아가기</OutlineButton>
    </StyledTemplate>
  );
};

export default FeeGuide;
