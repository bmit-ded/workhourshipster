import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './customer.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CustomerDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const customerEntity = useAppSelector(state => state.customer.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="customerDetailsHeading">Customer</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{customerEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{customerEntity.name}</dd>
          <dt>
            <span id="adress">Adress</span>
          </dt>
          <dd>{customerEntity.adress}</dd>
          <dt>
            <span id="phone">Phone</span>
          </dt>
          <dd>{customerEntity.phone}</dd>
          <dt>
            <span id="email">Email</span>
          </dt>
          <dd>{customerEntity.email}</dd>
        </dl>
        <Button tag={Link} to="/customer" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/customer/${customerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default CustomerDetail;
