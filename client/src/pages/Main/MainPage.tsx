import { Suspense } from 'react';
import { ErrorBoundary } from 'react-error-boundary';
import { useHistory } from 'react-router-dom';

import CreatorList from '../../components/Main/CreatorList/CreatorList';
import { HeroContent, MainTemplate, RouteButton, StyledSubTitle } from './Main.styles';

const MainPage = () => {
  const history = useHistory();

  return (
    <MainTemplate>
      <section>
        <HeroContent />
      </section>
      <section>
        <StyledSubTitle>Thank You For ___의 창작자들</StyledSubTitle>
        <ErrorBoundary fallback={<h2>창작자 목록을 불러오는데 실패했습니다.</h2>}>
          <Suspense fallback={true}>
            <CreatorList />
          </Suspense>
        </ErrorBoundary>
      </section>
      <section>
        <StyledSubTitle>내 재능에 가치를 부여하고 싶다면?</StyledSubTitle>
        <RouteButton onClick={() => history.push('/register')}>가치 부여하기</RouteButton>
      </section>
    </MainTemplate>
  );
};

export default MainPage;
