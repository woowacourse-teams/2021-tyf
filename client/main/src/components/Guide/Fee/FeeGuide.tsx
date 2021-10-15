import SubTitle from '../../@atom/SubTitle/SubTitle.styles';
import { FeeTable, KakaoIcon, StyledTemplate } from './FeeGuide.styles';

const FeeGuide = () => {
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
    </StyledTemplate>
  );
};

export default FeeGuide;
