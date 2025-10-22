export type Person = {
    id: number | null;
    name: string;
    categoryId: Category; // { id: Category };
}

export type Category = {
    id: number;
    name: string;
}
