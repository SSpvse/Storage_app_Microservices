import React from "react";
import { useNavigate } from "react-router-dom";
import { Package, PlusCircle, Warehouse, Home, Settings, User } from "lucide-react";

const MainPage = () => {
    const navigate = useNavigate();

    const handleViewUnits = () => {
        navigate("/my-storage");
    };

    const handleAddUnit = () => {
        navigate("/add-unit");
    };

    return (
        <div className="min-h-screen">
            <div className="container">


                {/* Main Content */}
                <div className="main-content">
                    <div className="cards">
                        {/* Add Unit Card */}
                        <div className="card add-unit">
                            <div className="card-header">
                                <PlusCircle className="icon" size={48} strokeWidth={1.5} />
                                <h2>Add New Storage Unit</h2>
                                <p>Create and manage your new storage space</p>
                            </div>
                            <div className="card-body">
                                <button onClick={handleAddUnit} className="btn-primary">
                                    <PlusCircle size={24} />
                                    Create New Unit
                                </button>
                            </div>
                        </div>

                        {/* View Units Card */}
                        <div className="card view-units">
                            <div className="card-header">
                                <Package className="icon" size={48} strokeWidth={1.5} />
                                <h2>Your Storage Units</h2>
                                <p>View and manage your existing storage spaces</p>
                            </div>
                            <div className="card-body">
                                <button onClick={handleViewUnits} className="btn-primary">
                                    <Package size={24} />
                                    View My Units
                                </button>
                            </div>
                        </div>
                    </div>


                </div>
            </div>
        </div>
    );
};

export default MainPage;
