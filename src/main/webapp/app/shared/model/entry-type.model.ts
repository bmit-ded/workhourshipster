import { IEntry } from 'app/shared/model/entry.model';

export interface IEntryType {
  id?: number;
  name?: string;
  worktime?: boolean;
  billable?: boolean | null;
  entries?: IEntry[] | null;
}

export const defaultValue: Readonly<IEntryType> = {
  worktime: false,
  billable: false,
};
