import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EntryType from './entry-type';
import EntryTypeDetail from './entry-type-detail';
import EntryTypeUpdate from './entry-type-update';
import EntryTypeDeleteDialog from './entry-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EntryTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EntryTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EntryTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={EntryType} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EntryTypeDeleteDialog} />
  </>
);

export default Routes;
