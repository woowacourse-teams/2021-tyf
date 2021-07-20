import styled from 'styled-components';

import GoogleLogo from '../../../assets/icons/google.svg';
import PALETTE from '../../../constants/palette';
import IconOutlineBarButton from '../IconOutlineBarButton/IconOutlineBarButton';

const GoogleBarButton = styled(IconOutlineBarButton).attrs({
  src: GoogleLogo,
  alt: 'google_logo',
})`
  color: ${PALETTE.GRAY_500};
  margin-bottom: 1rem;
`;

export default GoogleBarButton;
