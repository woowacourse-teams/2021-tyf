import { Route, Switch, Redirect } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

import Footer from './components/Footer/Footer';
import NavBar from './components/NavBar/NavBar';
import PrivateRoute from './components/PrivateRoute/PrivateRoute';
import CreatorPage from './pages/Creator/CreatorPage';
import DonationPage from './pages/Donation/Donation/DonationPage';
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
import StatisticsPage from './pages/Statistics/StatisticsPage';
import { accessTokenState } from './service/state/login';
import { CreatorId, OAuthProvider } from './types';

export interface ParamTypes {
  oauthProvider: OAuthProvider;
  creatorId: CreatorId;
}

const App = () => {
  const accessToken = useRecoilValue(accessTokenState);

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

        <Route path="/donation/:creatorId" component={DonationPage} exact />
        <Route path="/donation/:creatorId/message" component={DonationMessagePage} />
        <Route path="/donation/:creatorId/success" component={DonationSuccessPage} />

        <Route path="/creator/:creatorId" component={CreatorPage} exact />
        <PrivateRoute
          path="/creator/:creatorId/statistic"
          component={StatisticsPage}
          isAuthed={!!accessToken}
          redirectTo="/login"
        />

        <PrivateRoute
          path="/creator/:creatorId/setting"
          component={SettingPage}
          isAuthed={!!accessToken}
          redirectTo="/login"
        />

        <Redirect from="*" to="/" />
      </Switch>
      <Footer />
    </>
  );
};

export default App;
