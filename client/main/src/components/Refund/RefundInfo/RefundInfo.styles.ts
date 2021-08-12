import styled from 'styled-components';
import PALETTE from '../../../constants/palette';
import SubTitle from '../../@atom/SubTitle/SubTitle.styles';

export const StyledRefundInfo = styled.div`
  width: 100%;
`;

export const StyledSubTitle = styled(SubTitle)`
  text-align: left;
  margin-bottom: 2rem;
`;

export const InfoContainer = styled.div`
  width: 100%;
  line-height: 2rem;
  border-top: 1px solid ${PALETTE.GRAY_200};
  border-bottom: 1px solid ${PALETTE.GRAY_200};
  padding: 1rem 0;
`;

export const InfoTitle = styled.span`
  margin-right: 1rem;
`;
