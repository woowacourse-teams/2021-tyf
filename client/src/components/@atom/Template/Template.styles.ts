import styled from 'styled-components';

export const StyledTemplate = styled.main`
  width: 100vw;
  min-height: 100vh; // TODO: 나중에 header 높이만큼 빼주세요
`;

export const InnerTemplate = styled.section`
  min-width: 20rem; // NOTE: 아이폰SE width
  width: 100%;
  max-width: 26.5rem; // NOTE:아이폰11 PRO MAX width
  margin: 0 auto;
`;
