import { Department } from './department';

export interface Employee{
    id: number;
    name: string;
    email: string;

    department: Department;
}