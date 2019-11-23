import React from 'react';
import { Route, Redirect, Switch, HashRouter } from 'react-router-dom';
import Home from './containers/Home';


const AppRoutes = () =>
  <HashRouter>
    <Switch>
      <Route path="/" component={Home} />
      <Redirect from="/" to="/" component={Home} />
    </Switch>
  </HashRouter>

export default AppRoutes;
