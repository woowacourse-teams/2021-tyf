import { Route, Switch } from 'react-router-dom';
import { NavBar } from './components/NavBar/NavBar';
import loginPage from './pages/loginPage';
import { settlementPage } from './pages/settlementPage';

const App = () => {
  return (
    <>
      <NavBar />
      <Switch>
        <Route path="/" component={loginPage} exact />
        <Route path="/settlement" component={settlementPage} />
      </Switch>
    </>
  );
};

export default App;
