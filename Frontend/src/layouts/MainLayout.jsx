import {Layout} from "antd";
import { Menu } from "antd";
import { useNavigate } from "react-router-dom";
import { useLocation } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import {
    HomeOutlined,
    UserOutlined,
    TeamOutlined,
    IdcardOutlined,
    LogoutOutlined,
} from "@ant-design/icons";


const { Sider, Header, Content } = Layout;

const allMenuItems = [
    { key: "/dashboard", label: "Dashboard",      icon: <HomeOutlined />,   roles: [] },
    { key: "/users",     label: "Quản lý User",   icon: <UserOutlined />,   roles: ["ADMIN"] },
    { key: "/teams",     label: "Quản lý Team",   icon: <TeamOutlined />,   roles: ["ADMIN", "COACH"] },
    { key: "/profile",   label: "Hồ sơ cá nhân", icon: <IdcardOutlined />, roles: [] },
];

export default function MainLayout({ children }) {
    const { user, logout } = useAuth();
    const navigate = useNavigate();
    const location = useLocation();


    const visibleItems = allMenuItems.filter(item => item.roles.length === 0 ||
         item.roles.includes(user?.role));

     return (
        <Layout style={{ minHeight: "100vh" }}>
           
            <Sider theme="light" width={220}>
                <div style={{ color: "#000", textAlign: "center", padding: "16px", fontWeight: "bold" }}>
                    Sports Manager
                </div>
                <Menu
                    theme="light"
                    mode="inline"
                    selectedKeys={[location.pathname]}
                    items={visibleItems}
                    onClick={({ key }) => navigate(key)}
                />
            </Sider>

            <Layout>
            
                <Header style={{ background: "#faf8f8", display: "flex", justifyContent: "space-between", alignItems: "center", padding: "0 24px" }}>
                    <span>{user?.name}</span>
                    <span style={{ cursor: "pointer" }} onClick={logout}>
                        <LogoutOutlined /> Đăng xuất
                    </span>
                </Header>

                
                <Content style={{ margin: "24px", background: "#fdfafa", padding: "24px" }}>
                    {children}
                </Content>
            </Layout>
        </Layout>
    );
}