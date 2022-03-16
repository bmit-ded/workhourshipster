import { IUser } from 'app/shared/model/user.model';
import { IEntry } from 'app/shared/model/entry.model';

export interface IWorksheet {
  id?: number;
  user?: IUser | null;
  entries?: IEntry[] | null;
}

export const defaultValue: Readonly<IWorksheet> = {};
