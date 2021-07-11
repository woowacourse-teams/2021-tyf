import { FC, HTMLAttributes } from 'react';

import Template from '../../components/@atom/Template/Template';
import { InfoTitle, MoneyInfo, StatisticsContainer } from './StatisticsPage.styles';

const StatisticsPage: FC<HTMLAttributes<HTMLElement>> = () => {
  return (
    <Template>
      <StatisticsContainer>
        <InfoTitle>파노님이 총 후원 받은 금액은</InfoTitle>
        <MoneyInfo>
          1,203,000 <span>원</span>
        </MoneyInfo>
      </StatisticsContainer>
    </Template>
  );
};

export default StatisticsPage;
