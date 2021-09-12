import { Route, Switch } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

import { NavBar } from './components/NavBar/NavBar';
import PrivateRoute from './components/PrivateRoute/PrivateRoute';
import BankAccountPage from './pages/BankAccountPage';
import LoginPage from './pages/LoginPage';
import SettlementPage from './pages/SettlementPage';
import { accessTokenState } from './service/@state/login';

const App = () => {
  const accessToken = useRecoilValue(accessTokenState);

  return (
    <>
      <NavBar />
      <Switch>
        <Route path="/" component={LoginPage} exact />
        <PrivateRoute
          path="/settlement"
          component={SettlementPage}
          isAuthed={!!accessToken}
          redirectTo="/"
        />
        <PrivateRoute
          path="/bankAccount"
          component={BankAccountPage}
          isAuthed={!!accessToken}
          redirectTo="/"
        />
      </Switch>
    </>
  );
};

export default App;
