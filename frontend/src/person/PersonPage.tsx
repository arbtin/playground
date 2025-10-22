import { useEffect, useState } from 'react';
import {Person} from "@/person/Person.ts";
import PersonForm from "@/person/PersonForm.tsx";
import {getPerson} from "@/person/PersonService.tsx";

const PersonPage = () => {
    const [persons, setPersons] = useState<Person[]>([]);
    const [selectedPerson, setSelectedPerson] = useState<Person | null>(null);

    const getSelectedPerson = (person: Person | null) => {
        setSelectedPerson(person);
    }

//    useEffect(() => {
//        getPerson().then(d => setPersons(d))
//    }, [persons]);

    return (
        <>
            <header className="bg-white shadow-sm">
                <div className="mx-auto max-w-7xl px-4 py-6 sm:px-6 lg:px-8">
                    <h2 className="text-3xl font-bold tracking-tight text-gray-900">Persons</h2>
                </div>
            </header>
            <main>
                <div className="mx-auto max-w-7xl px-4 py-6 sm:px-6 lg:px-8">
                    <div className="relative overflow-x-auto bg-white dark:bg-black text-black dark:text-white">
                        <table className="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
                            <thead
                                className="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                            <tr>
                                <th scope="col" className="px-6 py-3">
                                    Id
                                </th>
                                <th scope="col" className="px-6 py-3">
                                    Name
                                </th>
                                <th scope="col" className="px-6 py-3">
                                    Category
                                </th>
                                <th scope="col" className="px-6 py-3">
                                </th>
                                <th scope="col" className="px-6 py-3">
                                </th>
                                <th scope="col" className="px-6 py-3">
                                </th>
                            </tr>
                            </thead>
                            <tbody>

                            </tbody>
                        </table>
                    </div>

                    <PersonForm selectedPerson={selectedPerson} getSelectedPerson={getSelectedPerson} />
                </div>
            </main>
        </>
    );
};

export default PersonPage;