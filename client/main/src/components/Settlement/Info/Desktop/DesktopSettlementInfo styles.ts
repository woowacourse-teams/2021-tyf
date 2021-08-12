import styled from 'styled-components';
import PALETTE from '../../../../constants/palette';
import Container from '../../../@atom/Container/Container.styles';
import SubTitle from '../../../@atom/SubTitle/SubTitle.styles';

export const StyledSettlementInfo = styled.div`
  display: flex;
  width: 77.5rem;
  justify-content: space-between;
`;

export const InfoContainer = styled(Container)`
  width: 21.25rem;
  height: 34rem;
  border-top: 2px solid ${PALETTE.GRAY_300};
  border-bottom: 2px solid ${PALETTE.GRAY_300};
  justify-content: space-around;
  position: relative;
`;

export const StyledSubTitle = styled(SubTitle)``;

export const StyledCommonSubTitle = styled(SubTitle)``;

export const Caution = styled.p``;

export const AmountContainer = styled(Container)``;

export const Amount = styled.span`
  display: inline-block;
  font-size: 2.5rem;
  font-weight: 700;
  margin: 1.75rem 0.5rem;
`;
