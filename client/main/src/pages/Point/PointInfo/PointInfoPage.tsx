import { Suspense } from 'react';
import { ErrorBoundary } from 'react-error-boundary';
import { useHistory } from 'react-router-dom';

import PointInfoForm from '../../../components/Point/PointInfo/PointInfoForm';
import { StyledTemplate } from './PointInfoPage.styles';

const PointInfoPage = () => {
  const history = useHistory();

  // TODO
  // memebers/me로 정보 불러올수있어야 접근가능
  // 해당 응답결과의 point 정보 사용하기

  return (
    <StyledTemplate>
      <ErrorBoundary
        fallback={<p>AUTH_ERROR_MESSAGE[error]</p>}
        onError={() => {
          alert('보유 포인트를 조회할 수 없습니다!');

          history.push('/');
        }}
      >
        <Suspense fallback={<p>내 포인트 정보를 불러오는 중입니다.</p>}>
          <PointInfoForm />
        </Suspense>
      </ErrorBoundary>
    </StyledTemplate>
  );
};

export default PointInfoPage;
