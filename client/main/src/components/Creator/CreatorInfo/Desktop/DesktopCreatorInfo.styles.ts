import styled from 'styled-components';

import { DEVICE } from '../../../../constants/device';
import PALETTE from '../../../../constants/palette';
import Button from '../../../@atom/Button/Button.styles';
import Container from '../../../@atom/Container/Container.styles';

export const StyledCreatorInfo = styled.section`
  @media ${DEVICE.DESKTOP} {
    margin-top: 4rem;
    margin-bottom: 10rem;
    display: flex;
    justify-content: space-around;
    align-items: center;
    width: 100%;
  }
`;

export const InfoContainer = styled.div`
  @media ${DEVICE.DESKTOP} {
    padding-left: 1rem;
    width: 80%;
  }
`;
export const StyledInfo = styled.div`
  @media ${DEVICE.DESKTOP} {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
`;

export const ProfileImg = styled.img`
  @media ${DEVICE.DESKTOP} {
    width: 10.5rem;
    height: 10.5rem;
    border: 1px solid ${({ theme }) => theme.color.border};
    border-radius: 50%;
    margin-right: 1rem;
    object-fit: cover;
  }
`;

export const NickName = styled.p`
  @media ${DEVICE.DESKTOP} {
    font-size: 1.25rem;
    font-weight: 700;
  }
`;

export const StyledButton = styled(Button)`
  @media ${DEVICE.DESKTOP} {
    height: 2rem;
    width: 9rem;
    font-size: 0.875rem;
    font-weight: 400;
  }
`;

export const DescriptionContainer = styled(Container)`
  line-height: 1.25;
  margin-top: 2rem;
  border-top: 1px solid ${({ theme }) => theme.color.border};
  border-bottom: 1px solid ${({ theme }) => theme.color.border};
  color: ${PALETTE.GRAY_500};
  min-height: 6rem;
  padding: 1rem 3rem;

  p {
    text-align: center;
  }
`;
