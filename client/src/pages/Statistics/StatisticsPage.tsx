import { FC, HTMLAttributes } from 'react';

import { StyledTemplate, InfoTitle, MoneyInfo } from './StatisticsPage.styles';

const StatisticsPage: FC<HTMLAttributes<HTMLElement>> = () => {
  return (
    <StyledTemplate>
      <MoneyInfo>
        <InfoTitle>
          파노님이
          <br />총 후원 받은 금액은
        </InfoTitle>
        1,203,000 <span>원</span>
      </MoneyInfo>
    </StyledTemplate>
  );
};

export default StatisticsPage;
