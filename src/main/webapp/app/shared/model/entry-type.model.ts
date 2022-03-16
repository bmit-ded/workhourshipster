import { IEntry } from 'app/shared/model/entry.model';

export interface IEntryType {
  id?: number;
  name?: string;
  worktime?: boolean;
  entries?: IEntry[] | null;
}

export const defaultValue: Readonly<IEntryType> = {
  worktime: false,
};
