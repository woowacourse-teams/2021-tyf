import styled from 'styled-components';
import { SIZE } from '../../../constants/device';
import PALETTE from '../../../constants/palette';
import Title from '../../@atom/Title/Title.styles';

export const StyledTitle = styled(Title)`
  margin-bottom: 3rem;
`;

export const StyledNavigation = styled.nav`
  margin-bottom: 5rem;
  border-bottom: 1px solid ${PALETTE.GRAY_300};
`;

export const GuideList = styled.ul`
  height: 4rem;
  display: flex;
  justify-content: space-around;
  align-items: center;
  flex: 0 0 25%;
`;

export const GuideListItem = styled.li`
  width: 100%;
  height: 100%;

  a {
    display: inline-block;
    width: 100%;
    height: 100%;
    color: ${PALETTE.GRAY_400};
    display: flex;
    justify-content: center;
    align-items: center;
  }
`;
