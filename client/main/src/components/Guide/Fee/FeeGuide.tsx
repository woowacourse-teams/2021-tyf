import SubTitle from '../../@atom/SubTitle/SubTitle.styles';
import { FeeTable, KakaoIcon, StyledTemplate } from './FeeGuide.styles';

const FeeGuide = () => {
  return (
    <StyledTemplate>
      <SubTitle>정산 수수료 안내</SubTitle>
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
            <td>Thank You For ___ 수수료</td>
            <td>없음</td>
          </tr>
          <tr>
            <td colSpan={2}>정산을 위해서는 10,000tp 이상을 보유하고 있어야 합니다.</td>
          </tr>
        </tbody>
      </FeeTable>
    </StyledTemplate>
  );
};

export default FeeGuide;
