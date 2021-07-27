import { UserInfo } from '../../../types';
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

const URLShareModal = ({ userInfo, onClose }: URLShareModalProps) => {
  const [content, setContent] = useState<keyof typeof ContentType>('CHOICE');

  const openBannerShareModal = () => {
    setContent('BANNER');
  };

  const urlShareModalObject = {
    CHOICE: (
      <URLShareSelectModal
        userInfo={userInfo}
        onClose={onClose}
        openBannerShareModal={openBannerShareModal}
      />
    ),
    BANNER: <URLBannerShareModal userInfo={userInfo} onClose={onClose} />,
  };

  return urlShareModalObject[content];
};

export default URLShareModal;
