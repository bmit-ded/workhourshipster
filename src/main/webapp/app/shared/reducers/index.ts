import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import authentication from './authentication';
import applicationProfile from './application-profile';

import administration from 'app/modules/administration/administration.reducer';
import userManagement from 'app/modules/administration/user-management/user-management.reducer';
import register from 'app/modules/account/register/register.reducer';
import activate from 'app/modules/account/activate/activate.reducer';
import password from 'app/modules/account/password/password.reducer';
import settings from 'app/modules/account/settings/settings.reducer';
import passwordReset from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import worksheet from 'app/entities/worksheet/worksheet.reducer';
// prettier-ignore
import entry from 'app/entities/entry/entry.reducer';
// prettier-ignore
import project from 'app/entities/project/project.reducer';
// prettier-ignore
import entryType from 'app/entities/entry-type/entry-type.reducer';
// prettier-ignore
import customer from 'app/entities/customer/customer.reducer';
// prettier-ignore
import tags from 'app/entities/tags/tags.reducer';
// prettier-ignore
import worklocation from 'app/entities/worklocation/worklocation.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const rootReducer = {
  authentication,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  worksheet,
  entry,
  project,
  entryType,
  customer,
  tags,
  worklocation,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
};

export default rootReducer;
