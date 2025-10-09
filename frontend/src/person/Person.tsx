import React, { useEffect, useState } from 'react';
import axios from 'axios';
import {Button} from "@/components/ui/button.tsx";

interface Category {
    id: number; // Assuming id can be either a number or string, adjust as needed
    name: string;
}

interface  Person {
    name: string;
    categoryId: number; // { id: Category };
}

const PersonForm: React.FC = () => {
    const [categories, setCategories] = useState<Category[] | null>(null);
    const [person, setPerson] = useState<Person>({ name: '', categoryId: 0 });
    const [error, setError] = useState<string | null>(null); // State to handle errors

    useEffect(() => {
        // Fetch categories from the backend
        axios.get('/v1/api/category')
            .then(response => {
                //console.log('Categories fetched:', response.data); // Log to check data structure
                if (Array.isArray(response.data)) {
                    setCategories(response.data); // Set categories if data is an array
                } else {
                    setError('Categories data is invalid.'); // Set error if data is invalid
                    setCategories([]);
                }
            })
            .catch(error => {
                console.error('Error fetching categories', error);
                setError('Failed to load categories.');
                setCategories([]);
            });
    }, []);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const { name, value } = e.target;
        setPerson(prevState => ({
            ...prevState,
            [name]: name === 'age' ? parseInt(value, 10) :  value
        }));
    };

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();

        // Submit form data to the backend
        axios.post('/v1/api/person', person)
            .then(response => {
                console.log('Person submitted:', response.data);
            })
            .catch(error => { console.error('Error submitting form', error);
        });
    };

    return (
        <form onSubmit={handleSubmit} className="space-y-4">
            <div>
                <label htmlFor="name" className="block text-sm font-medium text-gray-700">Name:</label>
                <input
                    type="text"
                    id="name"
                    value={person.name}
                    onChange={handleChange}
                    className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                />
            </div>
            <div>
                <label htmlFor="categoryId" className="block text-sm font-medium text-gray-700">Category:</label>
                <select
                    id="categoryId"
                    value={person.categoryId}
                    onChange={handleChange}
                    className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                >
                    <option value="">Select Category</option>
                    {categories ?
                        categories.map((category) => (
                            <option key={category.id} value={category.id}>
                                {category.name}
                            </option>
                        )) : <option value="">Loading Categories...</option>
                    }
                </select>
            </div>

            {error && <div className="text-red-500 text-sm">{error}</div>} {/* Show error message if any */}

            <div>

            </div>
        </form>
    );
};

export default PersonForm;