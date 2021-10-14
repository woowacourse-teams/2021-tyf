import styled from 'styled-components';
import Title from '../../@atom/Title/Title.styles';

export const StyledContactContent = styled.form`
  width: 100%;
`;

export const StyledTitle = styled(Title)`
  padding: 0;
  text-align: left;
  margin-bottom: 5rem;
`;

export const InputWrapper = styled.div`
  display: flex;
  align-items: center;
  margin-bottom: 3rem;
`;

export const InputTitle = styled.span`
  display: inline-block;
  width: 4rem;
  flex-shrink: 0;
  margin-right: 2rem;
  font-weight: bold;
`;
