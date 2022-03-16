import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './entry.reducer';
import { IEntry } from 'app/shared/model/entry.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Entry = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const entryList = useAppSelector(state => state.entry.entities);
  const loading = useAppSelector(state => state.entry.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="entry-heading" data-cy="EntryHeading">
        Entries
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Entry
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {entryList && entryList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Hours</th>
                <th>Date</th>
                <th>Worksheet</th>
                <th>Project</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {entryList.map((entry, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${entry.id}`} color="link" size="sm">
                      {entry.id}
                    </Button>
                  </td>
                  <td>{entry.hours}</td>
                  <td>{entry.date ? <TextFormat type="date" value={entry.date} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{entry.worksheet ? <Link to={`worksheet/${entry.worksheet.id}`}>{entry.worksheet.id}</Link> : ''}</td>
                  <td>{entry.project ? <Link to={`project/${entry.project.id}`}>{entry.project.name}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${entry.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${entry.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${entry.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Entries found</div>
        )}
      </div>
    </div>
  );
};

export default Entry;
