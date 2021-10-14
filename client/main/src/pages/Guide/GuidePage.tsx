import ContactContent from '../../components/Guide/Contact/ContactContent';
import CreatorGuideContent from '../../components/Guide/Creator/CreatorGuideContent';
import DonatorGuide from '../../components/Guide/Donator/DonatorGuide';
import FeeGuide from '../../components/Guide/Fee/FeeGuide';
import GuideHeader from '../../components/Guide/Header/GuideHeader';
import SignUpGuide from '../../components/Guide/SignUp/SignUpGuide';
import { StyledTemplate } from './GuidPage';

const contentMap: Record<string, () => JSX.Element> = {
  '/guide/sign-up': SignUpGuide,
  '/guide/donator': DonatorGuide,
  '/guide/creator': CreatorGuideContent,
  '/guide/fee': FeeGuide,
  '/guide/contact': ContactContent,
};

const GuidePage = () => {
  const GuideContent = contentMap[window.location.pathname] ?? CreatorGuideContent;

  return (
    <StyledTemplate>
      <GuideHeader />
      <GuideContent />
    </StyledTemplate>
  );
};

export default GuidePage;
