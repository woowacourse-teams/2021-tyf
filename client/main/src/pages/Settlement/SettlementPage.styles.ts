import styled from 'styled-components';
import Template from '../../components/@atom/Template/Template';
import Title from '../../components/@atom/Title/Title.styles';

export const StyledTemplate = styled(Template)`
  display: flex;
  flex-direction: column;
  align-items: center;

  section:nth-of-type(1) {
    margin-bottom: 10rem;
  }
`;

export const StyledTitle = styled(Title)`
  margin-bottom: 6rem;
`;
