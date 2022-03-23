import { IEntry } from 'app/shared/model/entry.model';

export interface ITags {
  id?: number;
  name?: string;
  entries?: IEntry[] | null;
}

export const defaultValue: Readonly<ITags> = {};
