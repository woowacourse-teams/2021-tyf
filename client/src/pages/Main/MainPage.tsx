import { Suspense } from 'react';
import { ErrorBoundary } from 'react-error-boundary';

import SubTitle from '../../components/@atom/SubTitle/SubTitle';
import CreatorList from '../../components/Main/CreatorList/CreatorList';
import { HeroContent, MainTemplate, RouteButton } from './Main.styles';

const MainPage = () => {
  return (
    <MainTemplate>
      <section>
        <HeroContent />
      </section>
      <section>
        <SubTitle>Thank You For ___의 창작자들</SubTitle>
        <ErrorBoundary fallback={<h2>창작자 목록을 불러오는데 실패했습니다.</h2>}>
          <Suspense fallback={true}>
            <CreatorList />
          </Suspense>
        </ErrorBoundary>
      </section>
      <section>
        <SubTitle>내 재능에 가치를 부여하고 싶다면?</SubTitle>
        <RouteButton>가치 부여하기</RouteButton>
      </section>
    </MainTemplate>
  );
};

export default MainPage;
