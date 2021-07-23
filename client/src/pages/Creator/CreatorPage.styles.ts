import styled from 'styled-components';
import Container from '../../components/@atom/Container/Container';
import Template from '../../components/@atom/Template/Template';
import { DEVICE } from '../../constants/device';

import PALETTE from '../../constants/palette';

export const StyledTemplate = styled(Template)`
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;

  section {
    width: 100%;
  }

  section:nth-of-type(1) {
    margin-top: 4rem;
    margin-bottom: 10rem;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;

    @media ${DEVICE.DESKTOP} {
      display: grid;
      grid-template-columns: auto minmax(5rem, 2fr) minmax(13.125rem, 1fr);
      column-gap: 2rem;
      row-gap: 1rem;
      align-items: center;

      & > a {
        grid-column: 1;
        grid-row: 1 / 3;

        img {
          margin: 0;
        }
      }

      & > div {
        grid-column: 2 / 4;
        grid-row: 2;
        margin: 0;
      }
    }
  }
`;

export const DescriptionContainer = styled(Container)`
  margin: 3rem 0 5rem 0;
  border-top: 1px solid ${({ theme }) => theme.color.border};
  border-bottom: 1px solid ${({ theme }) => theme.color.border};
  padding: 1rem;
  color: ${PALETTE.GRAY_500};

  @media ${DEVICE.DESKTOP} {
    padding: 1rem 3rem;
  }
`;
