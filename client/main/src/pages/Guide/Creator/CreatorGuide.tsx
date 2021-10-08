import CreatorGuideContent from '../../../components/Guide/Creator/CreatorGuideContent';
import GuideHeader from '../../../components/Guide/Header/GuideHeader';
import { StyledTemplate } from './CreatorGuide.styles';

const CreatorGuide = () => {
  return (
    <StyledTemplate>
      <GuideHeader />
      <CreatorGuideContent />
    </StyledTemplate>
  );
};

export default CreatorGuide;
