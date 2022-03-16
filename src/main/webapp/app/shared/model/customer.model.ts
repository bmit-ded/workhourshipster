import { IProject } from 'app/shared/model/project.model';

export interface ICustomer {
  id?: number;
  name?: string;
  adress?: string | null;
  phone?: number | null;
  email?: string | null;
  projects?: IProject[] | null;
}

export const defaultValue: Readonly<ICustomer> = {};
