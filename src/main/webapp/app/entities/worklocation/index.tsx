import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Worklocation from './worklocation';
import WorklocationDetail from './worklocation-detail';
import WorklocationUpdate from './worklocation-update';
import WorklocationDeleteDialog from './worklocation-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={WorklocationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={WorklocationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={WorklocationDetail} />
      <ErrorBoundaryRoute path={match.url} component={Worklocation} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={WorklocationDeleteDialog} />
  </>
);

export default Routes;
