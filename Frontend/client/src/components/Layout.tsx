import  { ReactNode } from "react";
import {Link, useNavigate} from "react-router-dom";
import {Home, Search, User, Warehouse} from "lucide-react";
import '../css/Layout.css';

interface LayoutProps {
    children: ReactNode;
}

const Layout = ({ children }: LayoutProps) => {
    const navigate = useNavigate();
    return (
        <div>
            {/* Navbar */}
            <nav className="navbar">
                <div className="navbar-content">
                    <div className="navbar-left">
                        <Warehouse className="icon" size={32} />
                        <span className="title">StorageManager</span>
                    </div>
                    <div className="navbar-right">
                        <button onClick={() => navigate("/")} className="nav-btn">
                            <Home className="icon" size={20} />
                            Home
                        </button>
                        <button onClick={() => navigate("/search")} className="nav-btn">
                            <Search className="icon" size={20} />
                            Search
                        </button>
                        <button onClick={() => navigate("/profile")} className="nav-btn">
                            <User className="icon" size={20} />
                            Profile
                        </button>
                        <button onClick={() => navigate("/login")} className="nav-btn">
                            <User className="icon" size={20} />
                            Login
                        </button>
                        <button onClick={() => navigate("/register")} className="nav-btn">
                            <User className="icon" size={20} />
                            Register
                        </button>

                    </div>
                </div>
            </nav>

            {/* Main content */}
            <div className="main-content">
                {children}
            </div>

            {/* Footer */}
            <footer className="footer">
                <div className="footer-content">
                    <p>&copy; 2024 StorageManager. All rights reserved.</p>
                    <div className="footer-links">
                        <Link to="/privacy-policy" className="footer-link">Privacy Policy</Link>
                        <Link to="/terms" className="footer-link">Terms of Service</Link>
                    </div>
                </div>
            </footer>
        </div>
    );
};

export default Layout;
