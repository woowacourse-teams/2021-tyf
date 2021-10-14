import { Link } from 'react-router-dom';

import { StyledHelpButton } from './HelpButton.styles';

const HelpButton = () => {
  return (
    <Link to="/guide/donator">
      <StyledHelpButton />
    </Link>
  );
};

export default HelpButton;
