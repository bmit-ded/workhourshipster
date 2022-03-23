import dayjs from 'dayjs';
import { IWorksheet } from 'app/shared/model/worksheet.model';
import { IProject } from 'app/shared/model/project.model';
import { IEntryType } from 'app/shared/model/entry-type.model';
import { IWorklocation } from 'app/shared/model/worklocation.model';
import { ITags } from 'app/shared/model/tags.model';

export interface IEntry {
  id?: number;
  hours?: number;
  date?: string;
  comment?: string | null;
  worksheet?: IWorksheet | null;
  project?: IProject | null;
  entryType?: IEntryType | null;
  worklocation?: IWorklocation | null;
  tags?: ITags[] | null;
}

export const defaultValue: Readonly<IEntry> = {};
