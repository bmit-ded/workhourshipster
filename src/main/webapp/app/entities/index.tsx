import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Worksheet from './worksheet';
import Entry from './entry';
import Project from './project';
import EntryType from './entry-type';
import Customer from './customer';
import Tags from './tags';
import Worklocation from './worklocation';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}worksheet`} component={Worksheet} />
      <ErrorBoundaryRoute path={`${match.url}entry`} component={Entry} />
      <ErrorBoundaryRoute path={`${match.url}project`} component={Project} />
      <ErrorBoundaryRoute path={`${match.url}entry-type`} component={EntryType} />
      <ErrorBoundaryRoute path={`${match.url}customer`} component={Customer} />
      <ErrorBoundaryRoute path={`${match.url}tags`} component={Tags} />
      <ErrorBoundaryRoute path={`${match.url}worklocation`} component={Worklocation} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
