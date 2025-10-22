import {Person} from "@/person/Person.ts";
import axios, {AxiosResponse} from "axios";

type DeletePerson = (id: number | null) => Promise<void>;

export const savePerson = async (data: Partial<Person>) => {
    try {
        const r = await axios.post('/api/person', data);
        return r.data;
    } catch (e) {
        return Promise.reject(e);
    }
}

export const getPerson = async () => {
    try {
        const r = await axios.get<Person[]>('/api/person');
        return r.data;
    } catch (e) {
        return Promise.reject(e);
    }
}

export const deletePerson: DeletePerson = (id: number | null) => (
    axios.delete(`/api/person/${id}`)
        .then((r: AxiosResponse) => r.data)
);

export const updatePerson = async (data: Person): Promise<Person> => {
    try {
        const r = await axios.put(`/api/person/${data.id}`, data);
        return r.data;
    } catch (e) {
        return Promise.reject(e);
    }
}
