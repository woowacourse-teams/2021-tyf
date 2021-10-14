import styled from 'styled-components';
import Kakaopay from '../../../assets/icons/kakao-pay.svg';

export const StyledTemplate = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin-top: 10rem;
`;

export const FeeTable = styled.table`
  margin: 4rem 0;
  text-align: center;
  border-collapse: separate;
  border-spacing: 1.5rem;
  line-height: 1.25rem;

  td {
    vertical-align: middle;
    padding-bottom: 0.5rem;
  }
`;

export const KakaoIcon = styled.img.attrs({ src: Kakaopay, alt: 'kakaopay logo' })`
  width: 3rem;
  aspect-ratio: 71 / 29;
  margin-top: 0.5rem;
`;
