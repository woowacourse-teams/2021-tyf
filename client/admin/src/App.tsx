import { Route, Switch } from 'react-router-dom';
import { NavBar } from './components/NavBar/NavBar';
import { BankAccountPage } from './pages/BankAccountPage';
import loginPage from './pages/loginPage';
import { settlementPage } from './pages/settlementPage';

const App = () => {
  return (
    <>
      <NavBar />
      <Switch>
        <Route path="/" component={loginPage} exact />
        <Route path="/settlement" component={settlementPage} />
        <Route path="/bankAccount" component={BankAccountPage} />
      </Switch>
    </>
  );
};

export default App;
