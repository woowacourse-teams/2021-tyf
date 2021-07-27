import { UserInfo } from '../../types';
import { useState } from 'react';
import URLShareSelectModal from '../URLShareSelectModal/URLShareSelectModal';
import URLBannerShareModal from '../URLBannerShareModal/URLBannerShareModal';

export interface URLShareModalProps {
  userInfo: UserInfo;
  onClose: () => void;
}

enum ContentType {
  'CHOICE',
  'BANNER',
}

// TODO: 색상버튼 값 바꿔야 됨
// TODO: 색상 변경 버튼 클릭 시, 소스코드 및 이미지도 변경되어야함

const URLShareModal = ({ userInfo, onClose }: URLShareModalProps) => {
  const [content, setContent] = useState<keyof typeof ContentType>('CHOICE');

  const openBannerShareModal = () => {
    setContent('BANNER');
  };

  return (
    <>
      {content === 'CHOICE' ? (
        <URLShareSelectModal
          userInfo={userInfo}
          onClose={onClose}
          openBannerShareModal={openBannerShareModal}
        />
      ) : (
        <URLBannerShareModal userInfo={userInfo} onClose={onClose} />
      )}
    </>
  );
};

export default URLShareModal;
