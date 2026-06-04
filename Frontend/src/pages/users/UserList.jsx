import { Table, Button, Tag, Space, Modal, message} from "antd"
import { useEffect, useState } from "react"
import {useNavigate } from "react-router-dom"
import axiosClient from "../../api/axiosClient"

export default function UserList() {
    const navigate = useNavigate()
    const [users, setUsers] = useState([])
    const [loading, setLoading] = useState(false)   
    const handleDelete = (id) => {
        Modal.confirm({
            title: "Are you sure you want to delete this user?",
            okText: "Yes",
            okType: "danger",
            cancelText: "No",
            onOk: async () => {
                try {
                    await axiosClient.delete(`/users/${id}`)
                    message.success("User deleted successfully")
                    fetchUsers()
                } catch (error) {
                    console.error("Failed to delete user:", error)
                }
            }
        })
    }        

    useEffect(() => {fetchUsers()}, [])

    const fetchUsers = async () => {
        setLoading(true)
        try {
            const response = await axiosClient.get("/users")
            setUsers(response.data.result)
        } catch (error) {
            console.error("Failed to fetch users:", error)
        } finally {
            setLoading (false)

        }
        }


const columns = [
    {
        title: "Tên",
        dataIndex: "name",
        key: "name",

    },
    {
        title: "Email",
        dataIndex: "email",
        key: "email",  
    },
    {
        title: "Role",
        dataIndex: "role",
        key: "role",
        render: (role) => {
            return <Tag color={role === "ADMIN" ? "red" : role === "USER" ? "blue" : "green"}>{role}</Tag>
        }
    },
    {
        title: "Số điện thoại",
        dataIndex: "phone",
        key: "phone",
    } ,
      { title: "Action",
        key: "action",
        render: (_, record) => (
            <Space size="middle">
                <Button type="primary" onClick={() => navigate(`/users/${record.id}/edit`)}>
                    Edit
                </Button>
                <Button type="primary" danger onClick={() => handleDelete(record.id)}>
                    Delete
                </Button>
            </Space>
        )
    }
]

return (
    <div>
        <div style={{ display: "flex", justifyContent: "space-between", marginBottom   : "20px" }}>  
            <h1>User List</h1>
            <Button type="primary" onClick={() => navigate("/users/create")}>
                Add User
            </Button>
        </div>
        <Table columns={columns} 
        dataSource={users}  
        rowKey="id"
        loading={loading}
        />


    </div>)
}   