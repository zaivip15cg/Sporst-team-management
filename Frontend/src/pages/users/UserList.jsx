import { Table, Button, Tag, Space } from "antd"
import { useEffect, useState } from "react"
import { useNavigate } from "react-router-dom"
import axiosClient from "../../api/axiosClient"




export default function UserList() {


    const { user } = useAuth();
    if (!user || user.role !== "admin") {
        return <Navigate to="/dashboard" />;
    }

    const allcolumns = [
        { title: "Tên", dataIndex: "name", key: "name" },
        { title: "Email", dataIndex: "email", key: "email" },
        { title: "Vai trò", dataIndex: "role", key: "role" },
        {
            title: "Hành động",
            key: "action",
            render: (text, record) => (
                <Space size="middle">
                    <Button type="primary" onClick={() => userNavigate(`/users/edit/${record.id}`)}>
                        Chỉnh sửa
                    </Button>
                    <Button type="danger" onClick={() => handleDelete(record.id)}>
                        Xóa
                    </Button>
                </Space>
            ),
        },
    ];

     

     }

    const [users, setUsers] = useState([]);

    useEffect(() => {
        fetchUsers();
    }, []);

    const fetchUsers = async () => {
        try {
            const response = await axiosClient.get("/users");
            setUsers(response.data);
        } catch (error) {
            console.error("Lỗi khi lấy danh sách người dùng:", error);
        }
    };

    const handleDelete = async (id) => {
        try {
            await axiosClient.delete(`/users/${id}`);
            fetchUsers(); // Cập nhật lại danh sách sau khi xóa
        } catch (error) {
            console.error("Lỗi khi xóa người dùng:", error);
        }
    };

    return <Table columns={allcolumns} dataSource={users} rowKey="id" />;



}
