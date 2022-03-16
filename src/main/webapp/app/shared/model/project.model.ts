import { IEntry } from 'app/shared/model/entry.model';

export interface IProject {
  id?: number;
  name?: string;
  entries?: IEntry[] | null;
}

export const defaultValue: Readonly<IProject> = {};
