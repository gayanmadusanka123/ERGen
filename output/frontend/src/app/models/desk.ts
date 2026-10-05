import { Department } from './department';

export interface Desk{
    id: number;
    name: string;

    department: Department;
}