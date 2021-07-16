import { FC, HTMLAttributes } from 'react';

import useLoginUserInfo from '../../service/hooks/useLoginUserInfo';
import useStatistics from '../../service/hooks/useStatistics';
import { StyledTemplate, InfoTitle, MoneyInfo } from './StatisticsPage.styles';

const StatisticsPage: FC<HTMLAttributes<HTMLElement>> = () => {
  const { userInfo } = useLoginUserInfo();
  const { point } = useStatistics();

  return (
    <StyledTemplate>
      <MoneyInfo>
        <InfoTitle>
          {userInfo && userInfo.name}님이
          <br />총 후원 받은 금액은
        </InfoTitle>
        {point.toLocaleString('en-us')}
        <span>원</span>
      </MoneyInfo>
    </StyledTemplate>
  );
};

export default StatisticsPage;
