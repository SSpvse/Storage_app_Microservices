
import './App.css';


import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import UnitManager from "./components/UnitManager.tsx";
import AddUnit from "./components/AddUnit.tsx";
import Layout from "./components/Layout.tsx";
import UserProfile from "./components/UserProfile.tsx";
import SearchFunction from "./components/SearchFunction.tsx";
import ItemDetail from "./components/ItemDetail.tsx";
import LoginProfile from "./components/LoginProfile.tsx";
import ItemManager from "./components/ItemManager.tsx";
import MainPage from "./components/MainPage.tsx";


function App() {


    return (
        <Router>
            <Routes>
                <Route path="/" element={<Layout><MainPage/></Layout>}/>
                <Route path="/search" element={<Layout><SearchFunction/></Layout>}/>
                <Route path="/profile" element={<Layout><UserProfile/></Layout>}/>
                <Route path="/login" element={<Layout><LoginProfile/></Layout>} />
                <Route path="/my-storage" element={<Layout><UnitManager /></Layout>} />
                <Route path="/add-unit" element={<Layout><AddUnit/></Layout>}/>
                <Route path="/unit/:unitId" element={<Layout><ItemManager/></Layout>}/>
                <Route path="/item/:id" element={<Layout><ItemDetail/></Layout>} />

            </Routes>
        </Router>
    );
}

export default App;