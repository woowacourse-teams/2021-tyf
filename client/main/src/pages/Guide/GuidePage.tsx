import ContactForm from '../../components/Guide/Contact/ContactForm';
import CreatorGuide from '../../components/Guide/Creator/CreatorGuide';
import DonatorGuide from '../../components/Guide/Donator/DonatorGuide';
import FeeGuide from '../../components/Guide/Fee/FeeGuide';
import GuideHeader from '../../components/Guide/Header/GuideHeader';
import SignUpGuide from '../../components/Guide/SignUp/SignUpGuide';
import { StyledTemplate } from './GuidPage.styles';

const contentMap: Record<string, () => JSX.Element> = {
  '/guide/sign-up': SignUpGuide,
  '/guide/donator': DonatorGuide,
  '/guide/creator': CreatorGuide,
  '/guide/fee': FeeGuide,
  '/guide/contact': ContactForm,
};

const GuidePage = () => {
  const GuideContent = contentMap[window.location.pathname] ?? SignUpGuide;

  return (
    <StyledTemplate>
      <GuideHeader />
      <GuideContent />
    </StyledTemplate>
  );
};

export default GuidePage;
