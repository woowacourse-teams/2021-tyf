import { useHistory } from 'react-router-dom';

import { OAuth } from '../../constants/constant';

enum OAuthSites {
  google,
  naver,
  kakao,
}

const useRegisterAuth = () => {
  const history = useHistory();
  const openOAuthPage = (name: keyof typeof OAuthSites) => {
    switch (name) {
      case OAuth.google.name:
        window.open(OAuth.google.url, '_target');
        break;

      case OAuth.naver.name:
        // (다음 페이지로 넘어가기 위한 임시 코드로 대체)
        // window.open(OAuth.naver, '_target');
        history.push('/register/url');
        break;

      case OAuth.kakao.name:
        window.open(OAuth.kakao.url, '_target');
        break;

      default:
    }
  };

  return { openOAuthPage };
};

export default useRegisterAuth;
