import styled from 'styled-components';

export const StyledTextarea = styled.textarea`
  border: 1px solid ${({ theme }) => theme.color.border};
  resize: none;
  padding: 0.75rem;
  border-radius: 5px;
  width: 100%;
  height: 9rem;
`;
