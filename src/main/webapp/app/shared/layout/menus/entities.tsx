import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';

import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown icon="th-list" name="Entities" id="entity-menu" data-cy="entity" style={{ maxHeight: '80vh', overflow: 'auto' }}>
    <>{/* to avoid warnings when empty */}</>
    <MenuItem icon="asterisk" to="/worksheet">
      Worksheet
    </MenuItem>
    <MenuItem icon="asterisk" to="/entry">
      Entry
    </MenuItem>
    <MenuItem icon="asterisk" to="/project">
      Project
    </MenuItem>
    <MenuItem icon="asterisk" to="/entry-type">
      Entry Type
    </MenuItem>
    <MenuItem icon="asterisk" to="/customer">
      Customer
    </MenuItem>
    <MenuItem icon="asterisk" to="/tags">
      Tags
    </MenuItem>
    <MenuItem icon="asterisk" to="/worklocation">
      Worklocation
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
