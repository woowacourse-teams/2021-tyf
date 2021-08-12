import { Suspense } from 'react';
import { useHistory, useParams } from 'react-router';
import { ErrorBoundary } from 'react-error-boundary';

import { ParamTypes } from '../../App';
import useUserInfo from '../../service/user/useUserInfo';
import DonationMessageList from '../../components/Donation/MessageList/DonationMessageList';
import { useWindowResize } from '../../utils/useWindowResize';
import { StyledTemplate } from './CreatorPage.styles';
import Spinner from '../../components/Spinner/Spinner';
import { SIZE } from '../../constants/device';
import URLShareModal from '../../components/ShareModal/URLShareModal/URLShareModal';
import useModal from '../../utils/useModal';
import Transition from '../../components/@atom/Transition/Transition.styles';
import CreatorInfo from '../../components/Creator/CreatorInfo/CreatorInfo';

const CreatorPage = () => {
  const history = useHistory();
  const { creatorId } = useParams<ParamTypes>();
  const { isOpen, toggleModal, closeModal } = useModal();
  const { userInfo } = useUserInfo();

  const { windowWidth } = useWindowResize();
  const isAdmin = userInfo?.pageName === creatorId;

  const isModalOpen = windowWidth > SIZE.DESKTOP_LARGE && isOpen && userInfo;

  return (
    <StyledTemplate>
      <ErrorBoundary
        fallback={<p>AUTH_ERROR_MESSAGE[error]</p>}
        onError={() => {
          alert('잘못된 창작자 정보입니다!');

          history.push('/');
        }}
      >
        <Suspense fallback={<p>사용자 정보를 불러오는 중입니다.</p>}>
          <Transition>
            <CreatorInfo isAdmin={isAdmin} creatorId={creatorId} toggleModal={toggleModal} />
          </Transition>
        </Suspense>

        <Suspense fallback={<Spinner />}>
          <Transition delay={0.2}>
            <DonationMessageList isAdmin={isAdmin} />
          </Transition>
          {isModalOpen && <URLShareModal userInfo={userInfo!} onClose={closeModal} />}
        </Suspense>
      </ErrorBoundary>
    </StyledTemplate>
  );
};

export default CreatorPage;
