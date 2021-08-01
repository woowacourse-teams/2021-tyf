import styled from 'styled-components';

export const List = styled.ul`
  padding: 1.5rem;
  display: flex;
  overflow-x: scroll;
  -ms-overflow-style: none; // ms
  scrollbar-width: none; // firefox

  &::-webkit-scrollbar {
    display: none;
  }

  & li {
    margin: 0 0.5rem;
  }
`;
