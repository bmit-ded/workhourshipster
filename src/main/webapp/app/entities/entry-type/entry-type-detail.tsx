import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './entry-type.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const EntryTypeDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const entryTypeEntity = useAppSelector(state => state.entryType.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="entryTypeDetailsHeading">EntryType</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{entryTypeEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{entryTypeEntity.name}</dd>
          <dt>
            <span id="worktime">Worktime</span>
          </dt>
          <dd>{entryTypeEntity.worktime ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/entry-type" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/entry-type/${entryTypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EntryTypeDetail;
