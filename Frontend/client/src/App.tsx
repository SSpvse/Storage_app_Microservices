
import './App.css';

import MainPageTest from "./TEST/MainPageTest.tsx";
import ItemManagerTest from "./TEST/ItemManagerTest.tsx";
import {BrowserRouter as Router, Route, Routes, useNavigate} from "react-router-dom";
import UnitManagerTest from "./TEST/UnitManagerTest.tsx";
import AddUnit from "./TEST/AddUnit.tsx";
import Layout from "./TEST/Layout.tsx";


function App() {


    return (
        <Router>
            <Routes>
                <Route path="/" element={<Layout><MainPageTest/></Layout>}/>
                <Route path="/my-storage" element={<Layout><UnitManagerTest /></Layout>} />
                <Route path="/add-unit" element={<Layout><AddUnit/></Layout>}/>
                <Route path="/unit/:unitId" element={<Layout><ItemManagerTest/></Layout>}/>
            </Routes>
        </Router>
    );
}

export default App;