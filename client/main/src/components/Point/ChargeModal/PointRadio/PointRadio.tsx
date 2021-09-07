import { StyledPointSelect } from './PointRadio.styles';

const PointRadio = ({ children, id, value }: any) => {
  return (
    <StyledPointSelect>
      <input type="radio" name="point" id={id} value={value} />
      <label htmlFor={id}>{children}</label>
    </StyledPointSelect>
  );
};

export default PointRadio;
