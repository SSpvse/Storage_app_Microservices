
import './App.css';

import MainPageTest from "./TEST/MainPageTest.tsx";
import ItemManagerTest from "./TEST/ItemManagerTest.tsx";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<MainPageTest/>}/>
                <Route path="/unit/:unitId" element={<ItemManagerTest/>}/>
            </Routes>
        </Router>
    );
}

export default App;