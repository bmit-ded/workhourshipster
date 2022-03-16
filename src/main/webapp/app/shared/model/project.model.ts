import { IEntry } from 'app/shared/model/entry.model';
import { ICustomer } from 'app/shared/model/customer.model';

export interface IProject {
  id?: number;
  name?: string;
  entries?: IEntry[] | null;
  customer?: ICustomer | null;
}

export const defaultValue: Readonly<IProject> = {};
