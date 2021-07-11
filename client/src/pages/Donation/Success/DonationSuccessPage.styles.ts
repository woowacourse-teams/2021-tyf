import styled from 'styled-components';
import Anchor from '../../../components/@atom/Anchor/Anchor';
import Button from '../../../components/@atom/Button/Button';

import Template from '../../../components/@atom/Template/Template';

export const StyledTemplate = styled(Template)`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

export const MainText = styled.p`
  font-size: 2rem;
  font-weight: 900;
  margin: 0.5rem 0;
  margin-bottom: 0.75rem;
`;

export const SubText = styled.p`
  font-size: 1.375rem;
  font-weight: 700;
`;

export const EmojiText = styled.p`
  font-size: 3.75rem;
  margin-top: 1.5rem;
  margin-bottom: 10rem;
`;

export const CreatorRouteButton = styled(Button)`
  height: 3.5rem;
`;

export const CloseButton = styled(Anchor)`
  margin: 2rem 0;
`;
