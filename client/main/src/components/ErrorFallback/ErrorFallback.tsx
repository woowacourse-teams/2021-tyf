import useScrollLock from '../../utils/useScrollLock';
import Title from '../@atom/Title/Title.styles';
import { StyledContainer, StyledErrorFallback, StyledButton } from './ErrorFallback.styles';

const ErrorFallback = () => {
  useScrollLock();

  return (
    <StyledErrorFallback>
      <StyledContainer>
        <Title>
          서비스에 오류가 발생했습니다. <br />
          잠시 후 다시 시도해주세요
        </Title>
        <StyledButton
          onClick={() => {
            window.location.href = window.location.origin;
          }}
        >
          새로고침
        </StyledButton>
      </StyledContainer>
    </StyledErrorFallback>
  );
};

export default ErrorFallback;
