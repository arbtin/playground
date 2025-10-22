import {render, screen} from "@testing-library/react";
import {describe, expect} from "vitest";
import PersonForm from "@/person/PersonForm.tsx";
import PersonService from "@/person/PersonService.ts";

const mockPerson = {
    id: 1,
    name: 'Fred User',
    categoryId: 1
}

let nameInput: HTMLInputElement;
let typeInput: HTMLInputElement;
let categoryInput: HTMLInputElement;
let submitInput: HTMLInputElement;
let editInput: HTMLInputElement;
let deleteInput: HTMLInputElement;

const doRender = (updatePerson: PersonService | null = null) => {
    render(<PersonForm selectedPerson={updatePerson} getSelectedPerson={vi.fn()} />)
        nameInput = screen.getByRole('textbox', { name: /name/i})
        //typeInput = screen.getByRole('combobox', { name: /type/i });
        submitInput = screen.getByRole('button', { name: /person/i });
        //editInput = screen.getByRole('button', { name: /edit/i });
        //deleteInput = screen.getByRole('button', { name: /delete/i });
}

describe("Render Person Form", () => {
    it("should render the form with the heading", () => {
        doRender();
        expect(nameInput).toBeVisible();
    })
    it("should show the submit button for the form", () => {
        doRender();
        expect(submitInput).toBeVisible();
    })

})

