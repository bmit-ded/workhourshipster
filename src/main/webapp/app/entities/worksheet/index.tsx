import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Worksheet from './worksheet';
import WorksheetDetail from './worksheet-detail';
import WorksheetUpdate from './worksheet-update';
import WorksheetDeleteDialog from './worksheet-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={WorksheetUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={WorksheetUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={WorksheetDetail} />
      <ErrorBoundaryRoute path={match.url} component={Worksheet} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={WorksheetDeleteDialog} />
  </>
);

export default Routes;
