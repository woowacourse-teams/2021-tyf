import { MouseEvent, useState } from 'react';

import { UserInfo } from '../../../types';
import DummyButton from '../../../assets/images/dummy/button.png';
import Button from '../../@atom/Button/Button.styles';
import Textarea from '../../@atom/Textarea/Textarea';
import {
  CloseButton,
  ColorSelectButton,
  DisplayButton,
  DisplayButtonContainer,
  PaletteContainer,
  SourceCodeContainer,
  StyledModal,
  StyledSubTitle,
} from './URLBannerShareModal.styles';
import useURLBanner from '../../../service//user/useURLBanner';

export interface URLBannerShareModalProps {
  userInfo: UserInfo;
  onClose: () => void;
}

const URLBannerShareModal = ({ onClose, userInfo }: URLBannerShareModalProps) => {
  const { sourceCode, bannerURL, changeButtonColor, copySourceCode } = useURLBanner(userInfo);

  const selectAll = ({ currentTarget }: MouseEvent<HTMLTextAreaElement>) => {
    currentTarget.select();
  };

  return (
    <StyledModal onClose={onClose}>
      <DisplayButtonContainer>
        <DisplayButton src={bannerURL} alt="selected-button-style" />
        <PaletteContainer>
          <ColorSelectButton color="#444444" onClick={() => changeButtonColor('black')} />
          <ColorSelectButton color="#F3706E" onClick={() => changeButtonColor('red')} />
          <ColorSelectButton color="#FFDD00" onClick={() => changeButtonColor('yellow')} />
          <ColorSelectButton color="#79D6B5" onClick={() => changeButtonColor('green')} />
          <ColorSelectButton color="#6D8BFF" onClick={() => changeButtonColor('blue')} />
          <ColorSelectButton color="#8C7AE6" onClick={() => changeButtonColor('purple')} />
        </PaletteContainer>
      </DisplayButtonContainer>
      <SourceCodeContainer>
        <StyledSubTitle>복사 붙여넣기 코드</StyledSubTitle>
        <Textarea value={sourceCode} readOnly onClick={selectAll} />
      </SourceCodeContainer>
      <Button onClick={copySourceCode}>소스코드 복사하기</Button>
      <CloseButton onClick={onClose}>창 닫기</CloseButton>
    </StyledModal>
  );
};

export default URLBannerShareModal;
