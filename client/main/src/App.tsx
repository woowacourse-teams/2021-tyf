import { Route, Switch, Redirect, useLocation } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

import Footer from './components/Footer/Footer';
import NavBar from './components/NavBar/NavBar';
import PrivateRoute from './components/PrivateRoute/PrivateRoute';
import CreatorPage from './pages/Creator/CreatorPage';
import DonationAmountPage from './pages/Donation/Amount/DonationAmountPage';
import DonationMessagePage from './pages/Donation/Message/DonationMessagePage';
import DonationSuccessPage from './pages/Donation/Success/DonationSuccessPage';
import LoginPage from './pages/Login/LoginPage';
import MainPage from './pages/Main/MainPage';
import RegisterAddressPage from './pages/Register/Address/RegisterAddressPage';
import RegisterAuthPage from './pages/Register/Auth/RegisterAuthPage';
import RegisterNamePage from './pages/Register/Name/RegisterNamePage';
import RegisterSuccessPage from './pages/Register/Success/RegisterSuccessPage';
import RegisterTermsPage from './pages/Register/Terms/RegisterTermsPage';
import SettingPage from './pages/Setting/SettingPage';
import useInitScrollTopEffect from './utils/useInitScrollTopEffect';
import { accessTokenState } from './service/@state/login';
import { CreatorId, OAuthProvider } from './types';
import RefundCertificationPage from './pages/Refund/Certification/RefundCertificationPage';
import RefundApplyPage from './pages/Refund/Apply/RefundApplyPage';
import RefundConfirmPage from './pages/Refund/Confirm/RefundConfirmPage';
import { useWindowResize } from './utils/useWindowResize';
import SettlementPage from './pages/Settlement/SettlementPage';
import SettlementRegisterPage from './pages/Settlement/Register/SettlementRegisterPage';
import MyPointPage from './pages/MyPoint/MyPointPage';

export interface ParamTypes {
  oauthProvider: OAuthProvider;
  creatorId: CreatorId;
}

const App = () => {
  const { pathname } = useLocation();
  const accessToken = useRecoilValue(accessTokenState);
  const { useWindowResizeInitEffect } = useWindowResize();

  useInitScrollTopEffect(pathname);
  useWindowResizeInitEffect();

  return (
    <>
      <NavBar />
      <Switch>
        <Route path="/" component={MainPage} exact />

        <PrivateRoute
          path="/login/:oauthProvider?"
          component={LoginPage}
          isAuthed={!accessToken}
          redirectTo="/"
        />

        <PrivateRoute
          path="/register"
          component={RegisterAuthPage}
          isAuthed={!accessToken}
          redirectTo="/"
          exact
        />
        <PrivateRoute
          path="/register/terms/:oauthProvider?"
          component={RegisterTermsPage}
          isAuthed={!accessToken}
          redirectTo="/"
        />
        <PrivateRoute
          path="/register/url"
          component={RegisterAddressPage}
          isAuthed={!accessToken}
          redirectTo="/"
        />
        <PrivateRoute
          path="/register/name"
          component={RegisterNamePage}
          isAuthed={!accessToken}
          redirectTo="/"
        />
        <Route path="/register/success" component={RegisterSuccessPage} />

        <Route path="/donation/:creatorId" component={DonationAmountPage} exact />
        <Route path="/donation/:creatorId/message" component={DonationMessagePage} />
        <Route path="/donation/:creatorId/success" component={DonationSuccessPage} />

        <Route path="/creator/:creatorId" component={CreatorPage} exact />

        <PrivateRoute
          path="/mypoint"
          component={MyPointPage}
          isAuthed={!!accessToken}
          redirectTo="/login"
        />

        <PrivateRoute
          path="/creator/:creatorId/settlement"
          component={SettlementPage}
          isAuthed={!!accessToken}
          redirectTo="/login"
          exact
        />
        <PrivateRoute
          path="/creator/:creatorId/settlement/register"
          component={SettlementRegisterPage}
          isAuthed={!!accessToken}
          redirectTo="/login"
        />

        <PrivateRoute
          path="/creator/:creatorId/setting"
          component={SettingPage}
          isAuthed={!!accessToken}
          redirectTo="/login"
        />

        <Route path="/refund" component={RefundApplyPage} exact />
        <Route path="/refund/cert" component={RefundCertificationPage} />
        <Route path="/refund/confirm" component={RefundConfirmPage} />

        <Redirect from="*" to="/" />
      </Switch>
      <Footer />
    </>
  );
};

export default App;
