import { useHistory } from 'react-router-dom';

import { OAUTH } from '../../constants/constant';

enum OAuthSites {
  google,
  naver,
  kakao,
}

const useRegisterAuth = () => {
  const history = useHistory();
  const openOAuthPage = (name: keyof typeof OAuthSites) => {
    switch (name) {
      case OAUTH.GOOGLE.NAME:
        window.open(OAUTH.GOOGLE.URL, '_target');
        break;

      case OAUTH.NAVER.NAME:
        // (다음 페이지로 넘어가기 위한 임시 코드로 대체)
        // window.open(OAUTH.NAVER, '_target');
        history.push('/register/url');
        break;

      case OAUTH.KAKAO.NAME:
        window.open(OAUTH.KAKAO.URL, '_target');
        break;

      default:
    }
  };

  return { openOAuthPage };
};

export default useRegisterAuth;
