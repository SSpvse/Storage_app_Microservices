
import './App.css';

import MainPageTest from "./TEST/MainPageTest.tsx";
import ItemManagerTest from "./TEST/ItemManagerTest.tsx";
import {BrowserRouter as Router, Route, Routes, useNavigate} from "react-router-dom";
import UnitManagerTest from "./TEST/UnitManagerTest.tsx";
import AddUnit from "./TEST/AddUnit.tsx";


function App() {


    return (
        <Router>
            <Routes>
                <Route path="/" element={<MainPageTest/>}/>
                <Route path="/my-storage" element={<UnitManagerTest />} />
                <Route path="/add-unit" element={<AddUnit/>}/>
                <Route path="/unit/:unitId" element={<ItemManagerTest/>}/>
            </Routes>
        </Router>
    );
}

export default App;