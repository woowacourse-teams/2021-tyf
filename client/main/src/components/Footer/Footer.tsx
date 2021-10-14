import Anchor from '../@atom/Anchor/Anchor';
import { StyledFooter } from './Footer.styles';

const Footer = () => {
  return (
    <StyledFooter>
      <p>Thank You For ___</p>
      <p>
        땡큐포 | 대표이사 전환오 | 사업자번호 775-18-01530 | 통신판매번호 2021-서울관악-1897 |
        서울시 관악구 행운4길 9, 302
      </p>
      <p>
        [고객센터] 전화번호 070 - 8098 - 2158 | 이메일
        <a href="mailto://thank.you.for.tyf@gmail.com"> thank.you.for.tyf@gmail.com</a>
        &nbsp;|&nbsp;
        <Anchor to="/refund">충전금액 환불</Anchor>
        &nbsp;|&nbsp;
        <Anchor to="/guide/fee">정산 수수료 안내</Anchor>
      </p>
      <p>© Thank You For ___ all rights reserved</p>
    </StyledFooter>
  );
};

export default Footer;
