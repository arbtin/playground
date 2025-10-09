import { render, screen, fireEvent } from '@testing-library/react';
import Form from '../Person.tsx';
import { vi } from 'vitest';
import axios from "axios";

vi.mock('axios', () => ({
    get: vi.fn(),
    post: vi.fn(),
}));

test('renders form and submits data', async () => {
    // Mock axios.get to return categories
    axios.get.mockResolvedValue({ data: [{ id: 1, name: 'Category 1' }] });

    render(<Form />);

    // Check that the form elements are rendered
    expect(screen.getByLabelText(/name/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/category/i)).toBeInTheDocument();

    // Interact with the form
    fireEvent.change(screen.getByLabelText(/name/i), { target: { value: 'John Doe' } });
    fireEvent.change(screen.getByLabelText(/category/i), { target: { value: '1' } });
    fireEvent.click(screen.getByText(/submit/i));

    // Assert that axios.post was called
    expect(axios.post).toHaveBeenCalled();
});
