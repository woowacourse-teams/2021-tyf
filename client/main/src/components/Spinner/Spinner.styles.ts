import styled, { keyframes } from 'styled-components';

const ldsEllipsis1 = keyframes`
    0% {
      transform: scale(0);
    }
    100% {
      transform: scale(1);
    }
`;

const ldsEllipsis2 = keyframes`
0% {
      transform: translate(0, 0);
    }
    100% {
      transform: translate(24px, 0);
    }
`;

const ldsEllipsis3 = keyframes`
 0% {
      transform: scale(1);
    }
    100% {
      transform: scale(0);
    }
`;

export const StyledSpinner = styled.div`
  position: absolute;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  backdrop-filter: blur(1.5px);
`;

export const SpinnerIcon = styled.div`
  display: inline-block;
  position: relative;
  width: 5rem;
  height: 5rem;

  div {
    position: absolute;
    top: 33px;
    width: 1rem;
    height: 1rem;
    border-radius: 50%;
    background: ${({ theme }) => theme.primary.base};
    animation-timing-function: cubic-bezier(0, 1, 1, 0);
  }

  div:nth-child(1) {
    left: 8px;
    animation: ${ldsEllipsis1} 0.6s infinite;
  }
  div:nth-child(2) {
    left: 8px;
    animation: ${ldsEllipsis2} 0.6s infinite;
  }
  div:nth-child(3) {
    left: 32px;
    animation: ${ldsEllipsis2} 0.6s infinite;
  }
  div:nth-child(4) {
    left: 56px;
    animation: ${ldsEllipsis3} 0.6s infinite;
  }
`;
