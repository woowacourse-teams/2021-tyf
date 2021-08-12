import styled from 'styled-components';
import { SIZE } from '../../../../constants/device';
import Container from '../../../@atom/Container/Container.styles';
import SubTitle from '../../../@atom/SubTitle/SubTitle.styles';
import Carousel from '../../../@molecule/Carousel/Carousel';

export const StyledSettlementInfo = styled.div`
  position: relative;
  max-width: ${SIZE.MOBILE_MAX}px;
`;

export const InfoContainer = styled(Container)`
  min-width: 100%;
`;

export const StyledSubTitle = styled(SubTitle)`
  width: 100%;
  text-align: left;
`;

export const StyledCommonSubTitle = styled(SubTitle)`
  width: 100%;
  text-align: left;
  margin-left: 3rem;
`;

export const Caution = styled.p`
  font-size: 0.75rem;
`;

export const AmountContainer = styled.div`
  font-weight: 700;
`;

export const Amount = styled.span`
  display: inline-block;
  font-size: 2.5rem;
  font-weight: 700;
  margin: 1.75rem 0.5rem;
`;

export const StyledCarousel = styled(Carousel)`
  width: 16rem;
  margin-bottom: 6rem;
`;
