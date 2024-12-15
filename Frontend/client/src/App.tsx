
import './App.css';

import MainPageTest from "./TEST/MainPageTest.tsx";
import ItemManagerTest from "./TEST/ItemManagerTest.tsx";

import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import UnitManagerTest from "./TEST/UnitManagerTest.tsx";
import AddUnit from "./TEST/AddUnit.tsx";
import Layout from "./TEST/Layout.tsx";
import UserProfile from "./TEST/UserProfile.tsx";
import SearchFunction from "./TEST/SearchFunction.tsx";
import ItemDetail from "./TEST/ItemDetail.tsx";


function App() {


    return (
        <Router>
            <Routes>
                <Route path="/" element={<Layout><MainPageTest/></Layout>}/>
                <Route path="/search" element={<Layout><SearchFunction/></Layout>}/>
                <Route path="/profile" element={<Layout><UserProfile/></Layout>}/>
                <Route path="/my-storage" element={<Layout><UnitManagerTest /></Layout>} />
                <Route path="/add-unit" element={<Layout><AddUnit/></Layout>}/>
                <Route path="/unit/:unitId" element={<Layout><ItemManagerTest/></Layout>}/>
                <Route path="/item/:itemId" element={<ItemDetail />} />
            </Routes>
        </Router>
    );
}

export default App;