import dayjs from 'dayjs';
import { IWorksheet } from 'app/shared/model/worksheet.model';
import { IProject } from 'app/shared/model/project.model';
import { IEntryType } from 'app/shared/model/entry-type.model';

export interface IEntry {
  id?: number;
  hours?: number;
  date?: string;
  worksheet?: IWorksheet | null;
  project?: IProject | null;
  entryType?: IEntryType | null;
}

export const defaultValue: Readonly<IEntry> = {};
