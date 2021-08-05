import styled from 'styled-components';

import { DEVICE, SIZE } from '../../../constants/device';
import { MIN_PAGE_HEIGHT } from '../../../constants/style';
import Checkbox from '../../@atom/Checkbox/Checkbox';
import Container from '../../@atom/Container/Container.styles';
import Title from '../../@atom/Title/Title.styles';

export const StyledRegisterTermsForm = styled.form`
  width: 100%;
  height: ${MIN_PAGE_HEIGHT};
  display: flex;
  flex-direction: column;
  justify-content: space-around;
`;

export const TermsContainer = styled(Container)`
  flex-direction: column;
  justify-content: space-around;
  align-items: flex-start;
  max-width: ${SIZE.MOBILE_MAX}px;
  margin: 0 auto;
`;

export const TermsButtonContainer = styled(Container)`
  margin: 0 auto;
  max-width: ${SIZE.MOBILE_MAX}px;
`;

export const RegisterTermsTitle = styled(Title)`
  margin-bottom: 4rem;
  text-align: left;
  max-width: ${SIZE.MOBILE_MAX}px;
  margin: 0 auto;

  span {
    display: block;
  }

  @media ${DEVICE.DESKTOP_LARGE} {
    text-align: center;
    max-width: 100%;

    span {
      display: inline;
    }
  }
`;

export const TermLabel = styled.label`
  padding: 0.5rem;
  cursor: pointer;
  display: flex;
  align-items: center;
`;

export const TermCheckbox = styled(Checkbox)`
  margin-right: 1rem;
`;

export const Divider = styled.hr`
  width: 100%;
  border: 1px solid ${({ theme }) => theme.color.border};
  margin: 1rem 0;
`;

export const TermLink = styled.span`
  color: ${({ theme }) => theme.primary.base};

  &:hover {
    color: ${({ theme }) => theme.primary.dimmed};
  }

  &:active {
    color: ${({ theme }) => theme.primary.blackened};
  }
`;
