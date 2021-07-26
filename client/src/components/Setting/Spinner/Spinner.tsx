import useScrollLock from '../../../utils/useScrollLock';
import { SpinnerIcon, StyledSpinner } from './Spinner.styles';

const Spinner = () => {
  useScrollLock();

  return (
    <StyledSpinner>
      <SpinnerIcon>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
      </SpinnerIcon>
    </StyledSpinner>
  );
};

export default Spinner;
