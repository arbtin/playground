import {Person, Category} from "@/person/Person.ts";
import {FormEvent, useEffect, useState} from "react";
import {savePerson, updatePerson} from "@/person/PersonService.tsx";

interface PersonFormProps {
    selectedPerson?: Person | null;
    getSelectedPerson: (person: (Person | null)) => void
}

const PersonForm = ({selectedPerson, getSelectedPerson}: PersonFormProps) => {
    const [name, setName] = useState("");
    const [categoryId, setCategoryId] = useState<number | null>(null);
    const [isEditing, setIsEditing] = useState(false);
    const [error, setError] = useState<string | null>(null);

    // Mock categories (replace with actual data source, e.g., prop or API)
    const categories: Category[] = [
        { id: 1, name: "Employee" },
        { id: 2, name: "Contract" },
    ]; // Adjust based on how you fetch categories

    useEffect(() => {
        if(selectedPerson) {
            setName(selectedPerson.name);
            setIsEditing(true);
            setCategoryId(selectedPerson.categoryId.id);
        }
    }, [selectedPerson]);

    const handleNameChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const name = event.target.value;
        setName(name);
    };

    const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        setError(null);

        if(!name || categoryId === null || categoryId === undefined) {
            setError("Name and Category are required");
            return;
        }

        const selectedCategory = categories.find((cat) => cat.id === categoryId);
        if (!selectedCategory) {
            setError("Category is not valid.");
            return;
        }

        try {
            if (selectedPerson) {
                const person: Person = {
                    id: selectedPerson.id,
                    name,
                    categoryId: selectedCategory
                }
                await updatePerson(person);
            } else {
                const newPerson: { name: string; categoryId: Category } = {
                    name,
                    categoryId: selectedCategory,
                }
                await savePerson(newPerson);
            }
            handleClear()
        } catch (err) {
            setError("Failed to save person. Please try again.");
        }
    }

    const handleClear = () => {
        setName("");
        setCategoryId(null);
        setIsEditing(false);
        if(selectedPerson) {
            getSelectedPerson(null);
        }
    }

    return (
        <form onSubmit={handleSubmit} className="flex-box space-y-6 pt-2">
        {isEditing ? "Edit Person:" : "Add Person:"}
        <div>
            <label htmlFor="name" className="block text-sm font-medium text-gray-700">Name:</label>
            <input
                type="text"
                name="name"
                id="name"
                value={name}
                onChange={handleNameChange}
                placeholder="Provide a name..."
                required
                className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
            />
        </div>
        <div>
            <label htmlFor="categoryId" className="block text-sm font-medium text-gray-700">Category:</label>
            <select
                value={categoryId ?? ""}
                onChange={(e) => setCategoryId(Number(e.target.value))}
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
        <input
            type="submit"
            value={isEditing ? "Update Person" : "Add Person"}
            name={isEditing ? "Update Person": "Add Person"}
            className="relative rounded-md dark:outline-2 bg-blue-200 px-2.5 py-1.5 text-sm font-semibold text-white-900 ring-1 ring-white-300 hover:bg-blue-400"
        />
        {error && <div className="text-red-500 text-sm">{error}</div>} {/* Show error message if any */}
        </form>
    )
}
export default PersonForm;