import dayjs from 'dayjs';
import { IWorksheet } from 'app/shared/model/worksheet.model';
import { IProject } from 'app/shared/model/project.model';

export interface IEntry {
  id?: number;
  hours?: number;
  date?: string;
  worksheet?: IWorksheet | null;
  project?: IProject | null;
}

export const defaultValue: Readonly<IEntry> = {};
