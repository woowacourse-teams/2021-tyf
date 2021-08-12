import styled, { css, keyframes } from 'styled-components';

interface TransitionProps {
  type?: 'fade-in';
  delay?: number;
  duration?: number;
  flexCenter?: boolean;
}

const fadeInKeyframe = keyframes`
0% {
  transform: translateY(50px);
  opacity: 0;
}
100% {
  transform: translateX(0);
  opacity: 1;
}
`;

const flexCenterStyle = css`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
`;

const keyframeMap = {
  ['fade-in']: fadeInKeyframe,
};

const Transition = styled.div<TransitionProps>`
  width: 100%;
  opacity: 0;

  animation-name: ${({ type }) => keyframeMap[type!]};
  animation-duration: ${({ duration }) => duration}s;
  animation-delay: ${({ delay }) => delay}s;
  animation-fill-mode: forwards;

  ${({ flexCenter }) => flexCenter && flexCenterStyle};
`;

Transition.defaultProps = {
  type: 'fade-in',
  delay: 0,
  duration: 0.7,
  flexCenter: true,
};

export default Transition;
