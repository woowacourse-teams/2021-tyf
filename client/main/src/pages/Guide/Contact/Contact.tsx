import { StyledTemplate } from './Contact.styles';
import ContactContent from '../../../components/Guide/Contact/ContactContent';
import GuideHeader from '../../../components/Guide/Header/GuideHeader';

const Contact = () => {
  return (
    <StyledTemplate>
      <GuideHeader />
      <ContactContent />
    </StyledTemplate>
  );
};

export default Contact;
