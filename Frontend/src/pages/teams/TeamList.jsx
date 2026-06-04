import { Table, Button, Tag, Space, Modal, message} from "antd"
import { useEffect, useState } from "react"
import {useNavigate } from "react-router-dom"
import axiosClient from "../../api/axiosClient"
export default function TeamList() {
    const navigate = useNavigate()
    const [teams, setTeams] = useState([])
    const [loading, setLoading] = useState(false)   
    const handleDelete = (id) => {
        Modal.confirm({
            title: "Are you sure you want to delete this team?",
            okText: "Yes",
            okType: "danger",
            cancelText: "No",
            onOk: async () => {
                try {
                    await axiosClient.delete(`/teams/${id}`)
                    message.success("Team deleted successfully")
                    fetchTeams()
                } catch (error) {
                    console.error("Failed to delete team:", error)
                }
            }
        })
    }        

    useEffect(() => {fetchTeams()}, [])

    const fetchTeams = async () => {
        setLoading(true)
        try {
            const response = await axiosClient.get("/teams")
            setTeams(response.data.result)
        } catch (error) {
            console.error("Failed to fetch teams:", error)
        } finally {
            setLoading (false)

            }}


const columns = [
    {
        title: "Tên",
        dataIndex: "teamName",
        key: "teamName",

    },
    {
        title: "Số thành viên",
        dataIndex: "memberCount",
        key: "memberCount",  
    },
    
    
      { title: "Action",
        key: "action",
        render: (_, record) => (
            <Space size="middle">
                <Button type="primary" onClick={() => navigate(`/teams/${record.id}`)}>
                    Detail
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
            <h1>Team List</h1>
            <Button type="primary" onClick={() => navigate("/teams/create")}>
                Add Team                
            </Button>
        </div>
        <Table columns={columns} 
        dataSource={teams}  
        rowKey="id"
        loading={loading}
        />


    </div>)
    }  
