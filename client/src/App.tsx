import { Suspense } from 'react';
import { ErrorBoundary } from 'react-error-boundary';
import { Route, Switch, Redirect } from 'react-router-dom';

import Footer from './components/Footer/Footer';
import NavBar from './components/NavBar/NavBar';
import CreatorPage from './pages/Creator/CreatorPage';
import DonationPage from './pages/Donation/Donation/DonationPage';
import DonationMessagePage from './pages/Donation/Message/DonationMessagePage';
import DonationSuccessPage from './pages/Donation/Success/DonationSuccessPage';
import LoginPage from './pages/Login/LoginPage';
import MainPage from './pages/Main/MainPage';
import RegisterAddressPage from './pages/Register/RegisterAddress/RegisterAddressPage';
import RegisterAuthPage from './pages/Register/RegisterAuth/RegisterAuthPage';
import RegisterNamePage from './pages/Register/RegisterName/RegisterNamePage';
import RegisterSuccessPage from './pages/Register/RegisterSuccess/RegisterSuccessPage';
import RegisterTermsPage from './pages/Register/RegisterTerms/RegisterTermsPage';
import StatisticsPage from './pages/Statistics/StatisticsPage';
import { CreatorId, OAuthProvider } from './types';

export interface ParamTypes {
  oauthProvider: OAuthProvider;
  creatorId: CreatorId;
}

const App = () => {
  return (
    <>
      <NavBar />
      <Switch>
        <Route path="/" component={MainPage} exact />

        <Route path="/login/:oauthProvider?" component={LoginPage} />

        <Route path="/register" component={RegisterTermsPage} exact />
        <Route path="/register/auth" component={RegisterAuthPage} />
        <Route path="/register/url" component={RegisterAddressPage} />
        <Route path="/register/name" component={RegisterNamePage} />
        <Route path="/register/success" component={RegisterSuccessPage} />

        <Route path="/donation/:creatorId" component={DonationPage} exact />
        <Route path="/donation/:creatorId/message" component={DonationMessagePage} />
        <Route path="/donation/:creatorId/success" component={DonationSuccessPage} />

        <Route path="/creator/:creatorId" component={CreatorPage} exact />
        <Route path="/creator/:creatorId/statistic" component={StatisticsPage} />

        <Redirect from="*" to="/" />
      </Switch>
      <Footer />
    </>
  );
};

export default App;
