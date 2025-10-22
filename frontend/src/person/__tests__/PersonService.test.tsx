import {describe, expect} from "vitest";
import {Category, Person} from "@/person/Person.ts";
import {http, HttpResponse} from "msw";
import {deletePerson, getPerson, savePerson, updatePerson} from "@/person/PersonService.tsx";
import axios from "axios";
import {setupServer} from "msw/node";

describe("Person Service", () => {

    let persons: Person[];
    let categories: Category[];
    
    axios.defaults.baseURL = "http://localhost:3000";
    const server = setupServer()
    beforeAll(() => {
        server.listen({onUnhandledRequset: 'error'})

        categories = [
            {id: 1, name: "Employee"},
            {id: 2, name: "Overseas"}
        ];

        persons = [
            {id: 1, name: 'Fred', categoryId: categories[0]},
            {id: 2, name: 'Fred', categoryId: categories[1]},
            {id: 3, name: 'Fred', categoryId: categories[0]}
        ];
    })
    afterAll(() => server.close())
    afterEach(() => server.resetHandlers())

    it("should make a post request to the person api with a person", async () => {
        const expected: Person = persons[0];

        server.use(http.post('/api/person', () =>
            HttpResponse.json(persons[0], {status: 200})
        ))
        const response = await savePerson(expected)
        expect(response).toStrictEqual(expected);
    })

    it('should make a get request to retrieve all persons', async () => {
        server.use(http.get('api/person', () =>
            HttpResponse.json(persons, {status: 200})
        ))
        expect(await getPerson()).toStrictEqual(persons)
    })

    it('should make a get request to retrieve a single persons', async () => {
        const expected: Person = persons[0];

        server.use(http.get('/api/person', () =>
            HttpResponse.json(expected, {status: 201})
        ))

        expect(await getPerson()).toStrictEqual(expected)
    })

    it('should send a update request to update existing person by id', async () => {
        const mockPerson = {
            id: 1,
            name: "John",
            categoryId: categories[0],
        };

        const axiosPutMock = vi.spyOn(axios, 'put').mockResolvedValueOnce({
            data: mockPerson,
            status: 200
        })

        const result = await updatePerson(mockPerson);

        expect(axiosPutMock).toHaveBeenCalledWith(`/api/person/${mockPerson.id}`, mockPerson);
        
        expect(result).toBe(mockPerson);
    })

    it('should send a delete request to delete a person', async () => {
        const mockId = 1;

        const axiosDeleteMock = vi.spyOn(axios, 'delete').mockResolvedValueOnce({
            data: mockId,
            status: 200
        });

        const result: void = await deletePerson(mockId);
        expect(axiosDeleteMock).toHaveBeenCalledWith(`/api/person/${mockId}`);
        expect(result).toBe(mockId);

    })

})