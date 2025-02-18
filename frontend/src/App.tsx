import { BrowserRouter, Routes, Route, Link } from 'react-router-dom';
import Home from './common/Home.tsx';
import Person from './person/Person.tsx';

function App() {
    return (
        <BrowserRouter>
            <nav className="flex bg-gray-800 h-12 w-72 p-4 items-center ">
                <Link to="/">Home</Link> | <Link to="/person">Person</Link>
            </nav>
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/person" element={<Person />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;