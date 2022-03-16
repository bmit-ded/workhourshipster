import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IWorksheet } from 'app/shared/model/worksheet.model';
import { getEntities as getWorksheets } from 'app/entities/worksheet/worksheet.reducer';
import { IProject } from 'app/shared/model/project.model';
import { getEntities as getProjects } from 'app/entities/project/project.reducer';
import { IEntryType } from 'app/shared/model/entry-type.model';
import { getEntities as getEntryTypes } from 'app/entities/entry-type/entry-type.reducer';
import { getEntity, updateEntity, createEntity, reset } from './entry.reducer';
import { IEntry } from 'app/shared/model/entry.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const EntryUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const worksheets = useAppSelector(state => state.worksheet.entities);
  const projects = useAppSelector(state => state.project.entities);
  const entryTypes = useAppSelector(state => state.entryType.entities);
  const entryEntity = useAppSelector(state => state.entry.entity);
  const loading = useAppSelector(state => state.entry.loading);
  const updating = useAppSelector(state => state.entry.updating);
  const updateSuccess = useAppSelector(state => state.entry.updateSuccess);
  const handleClose = () => {
    props.history.push('/entry');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getWorksheets({}));
    dispatch(getProjects({}));
    dispatch(getEntryTypes({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.date = convertDateTimeToServer(values.date);

    const entity = {
      ...entryEntity,
      ...values,
      worksheet: worksheets.find(it => it.id.toString() === values.worksheet.toString()),
      project: projects.find(it => it.id.toString() === values.project.toString()),
      entryType: entryTypes.find(it => it.id.toString() === values.entryType.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          date: displayDefaultDateTime(),
        }
      : {
          ...entryEntity,
          date: convertDateTimeFromServer(entryEntity.date),
          worksheet: entryEntity?.worksheet?.id,
          project: entryEntity?.project?.id,
          entryType: entryEntity?.entryType?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="workhourshipsterApp.entry.home.createOrEditLabel" data-cy="EntryCreateUpdateHeading">
            Create or edit a Entry
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="entry-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Hours"
                id="entry-hours"
                name="hours"
                data-cy="hours"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Date"
                id="entry-date"
                name="date"
                data-cy="date"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField id="entry-worksheet" name="worksheet" data-cy="worksheet" label="Worksheet" type="select">
                <option value="" key="0" />
                {worksheets
                  ? worksheets.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="entry-project" name="project" data-cy="project" label="Project" type="select">
                <option value="" key="0" />
                {projects
                  ? projects.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="entry-entryType" name="entryType" data-cy="entryType" label="Entry Type" type="select">
                <option value="" key="0" />
                {entryTypes
                  ? entryTypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/entry" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default EntryUpdate;
