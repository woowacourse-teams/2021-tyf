import { Route, Switch } from 'react-router-dom';
import loginPage from './pages/loginPage';
import { refundPage } from './pages/refundPage';
import { settlementPage } from './pages/settlementPage';

const App = () => {
  return (
    <>
      <Switch>
        <Route path="/" component={loginPage} exact />
        <Route path="/refund" component={refundPage} />
        <Route path="/settlement" component={settlementPage} />
      </Switch>
    </>
  );
};

export default App;
