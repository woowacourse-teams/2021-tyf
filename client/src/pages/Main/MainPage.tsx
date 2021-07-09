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
        <CreatorList />
      </section>
      <section>
        <SubTitle>내 재능에 가치를 부여하고 싶다면?</SubTitle>
        <RouteButton>가치 부여하기</RouteButton>
      </section>
    </MainTemplate>
  );
};

export default MainPage;
