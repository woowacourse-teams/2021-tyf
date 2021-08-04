import { Suspense } from 'react';
import { ErrorBoundary } from 'react-error-boundary';
import { useHistory } from 'react-router-dom';

import CreatorList from '../../components/Main/CreatorList/CreatorList';
import Spinner from '../../components/Spinner/Spinner';
import useUserInfo from '../../service/hooks/user/useUserInfo';
import { DEVICE } from '../../constants/device';
import {
  HeroContent,
  DescriptionContainer,
  MainTemplate,
  RouteButton,
  StyledSubTitle,
} from './Main.styles';
import HeroContentDesktop from '../../assets/images/hero-content-desktop.svg';
import Logo from '../../components/@molecule/Logo/Logo';
import MainImage from '../../assets/images/hero-content.svg';

const MainPage = () => {
  const history = useHistory();
  const { userInfo } = useUserInfo();

  return (
    <MainTemplate>
      <section>
        <HeroContent>
          <source media={DEVICE.DESKTOP} srcSet={HeroContentDesktop} />
          <img src={MainImage} />
        </HeroContent>
      </section>
      <section>
        <DescriptionContainer>
          <StyledSubTitle>
            누구나 쉽게 <br />
            창작자를 응원할 수 있는 <br />
            간편 도네이션 서비스
          </StyledSubTitle>
          <Logo />
        </DescriptionContainer>
      </section>
      <section>
        <StyledSubTitle>
          <Logo />의 창작자들
        </StyledSubTitle>
        <ErrorBoundary fallback={<h2>창작자 목록을 불러오는데 실패했습니다.</h2>}>
          <Suspense fallback={<Spinner />}>
            <CreatorList />
          </Suspense>
        </ErrorBoundary>
      </section>
      {!userInfo && (
        <section>
          <StyledSubTitle>내 재능에 가치를 부여하고 싶다면?</StyledSubTitle>
          <RouteButton onClick={() => history.push('/register')}>가치 부여하기</RouteButton>
        </section>
      )}
    </MainTemplate>
  );
};

export default MainPage;
