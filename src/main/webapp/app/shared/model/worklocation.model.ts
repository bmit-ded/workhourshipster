import { IEntry } from 'app/shared/model/entry.model';

export interface IWorklocation {
  id?: number;
  name?: string | null;
  entries?: IEntry[] | null;
}

export const defaultValue: Readonly<IWorklocation> = {};
