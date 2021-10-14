import { Link } from 'react-router-dom';

import { StyledHelpButton } from './HelpButton.styles';

const HelpButton = () => {
  return (
    <Link to="/guide/contact">
      <StyledHelpButton />
    </Link>
  );
};

export default HelpButton;
