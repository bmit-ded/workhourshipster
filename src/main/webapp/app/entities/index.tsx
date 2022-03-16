import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Worksheet from './worksheet';
import Entry from './entry';
import Project from './project';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}worksheet`} component={Worksheet} />
      <ErrorBoundaryRoute path={`${match.url}entry`} component={Entry} />
      <ErrorBoundaryRoute path={`${match.url}project`} component={Project} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
