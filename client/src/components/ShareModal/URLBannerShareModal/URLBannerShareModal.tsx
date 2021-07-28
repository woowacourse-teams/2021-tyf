import { MouseEvent } from 'react';

import { UserInfo } from '../../../types';
import DummyButton from '../../../assets/images/dummy/button.png';
import Button from '../../@atom/Button/Button';
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

export interface URLBannerShareModalProps {
  userInfo: UserInfo;
  onClose: () => void;
}

const URLBannerShareModal = ({ onClose, userInfo }: URLBannerShareModalProps) => {
  const sourceCode = `<a href="${window.location.origin}/creator/${userInfo.pageName}" target="_blank"><img height="36" style="border:0px;height:36px;" src="${DummyButton}" border="0" alt="thank you for button" /></a>`;

  const copySourceCode = () => {
    navigator.clipboard.writeText(sourceCode);
    alert('소스코드가 복사되었습니다.');
  };

  const selectAll = ({ currentTarget }: MouseEvent<HTMLTextAreaElement>) => {
    currentTarget.select();
  };

  // TODO: 색상버튼 값 바꿔야 됨
  // TODO: 색상 변경 버튼 클릭 시, 소스코드 및 이미지도 변경되어야함
  const changeButtonColor = (color: string) => {
    alert(color);
  };

  return (
    <StyledModal onClose={onClose}>
      <DisplayButtonContainer>
        <DisplayButton src={DummyButton} alt="" />
        <PaletteContainer>
          <ColorSelectButton color="red" onClick={() => changeButtonColor('red')} />
          <ColorSelectButton color="blue" onClick={() => changeButtonColor('blue')} />
          <ColorSelectButton color="purple" onClick={() => changeButtonColor('purple')} />
          <ColorSelectButton color="green" onClick={() => changeButtonColor('green')} />
          <ColorSelectButton color="yellow" onClick={() => changeButtonColor('yellow')} />
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
